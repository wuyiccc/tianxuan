package com.wuyiccc.tianxuan.gateway.filter;

import com.google.gson.Gson;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.IPUtils;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.gateway.config.TianxuanBlackIpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/7/23 06:46
 */
@Slf4j
@Component
public class BlackIpFilter implements GlobalFilter, Ordered {

    @Autowired
    private TianxuanBlackIpConfig tianxuanBlackIpConfig;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        List<String> limitUrls = tianxuanBlackIpConfig.getLimitUrls();

        String url = exchange.getRequest().getURI().getPath();

        if (CollectionUtils.isNotEmpty(limitUrls)) {
            for (String limitUrl : limitUrls) {
                if (antPathMatcher.matchStart(limitUrl, url)) {
                    log.info("拦截到ip进行限流");
                    return doLimit(exchange, chain);
                }
            }
        }
        return chain.filter(exchange);
    }


    private Mono<Void> doLimit(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取请求ip
        ServerHttpRequest request = exchange.getRequest();
        String ip = IPUtils.getIP(request);
        // 正常的ip
        final String ipRedisKey = "gateway-ip:" + ip;
        // 被拦截的黑名单, 如果存在, 则证明目前被关小黑屋
        final String ipRedisLimitedKey = "gateway-ip:limit:" + ip;

        // 获取当前ip, 查询还剩下多少的小黑屋时间
        long limitLeftTimes = redisUtils.ttl(ipRedisLimitedKey);
        if (limitLeftTimes > 0) {
            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        }
        // 在redis中获得ip的累加次数
        long requestCounts = redisUtils.increment(ipRedisKey, 1);
        // 判断如果是第一次进来, 则需要设置间隔的时间
        if (requestCounts == 1) {
            redisUtils.expire(ipRedisKey, tianxuanBlackIpConfig.getTimeInterval());
        }
        // 如果还能获取到请求数, 说明用户的连续请求落在限定的timeInterval秒之内
        // 一旦请求次数超出限制的连续访问次数, 则需要限制当前ip
        if (requestCounts > tianxuanBlackIpConfig.getContinueCounts()) {
            redisUtils.set(ipRedisLimitedKey, ipRedisLimitedKey, tianxuanBlackIpConfig.getLimitTimes());
            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> renderErrorMsg(ServerWebExchange exchange, ResponseStatusEnum responseStatusEnum) {

        ServerHttpResponse response = exchange.getResponse();
        CommonResult<Object> jsonResult = CommonResult.exception(responseStatusEnum);

        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

        if (!response.getHeaders().containsKey("Content-Type")) {
            response.getHeaders().add("Content-Type", MimeTypeUtils.APPLICATION_JSON_VALUE);
        }

        String resultJson = new Gson().toJson(jsonResult);

        DataBuffer dataBuffer = response.bufferFactory()
                .wrap(resultJson.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
