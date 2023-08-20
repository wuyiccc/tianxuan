package com.wuyiccc.tianxuan.gateway.filter;

import com.google.gson.Gson;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.JWTUtils;
import com.wuyiccc.tianxuan.gateway.config.TianxuanGatewayExcludeConfig;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
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

import static com.wuyiccc.tianxuan.common.base.BaseInfoProperties.*;

/**
 * @author wuyiccc
 * @date 2023/7/1 16:47
 */
@Slf4j
@Component
public class SecurityJWTFilter implements GlobalFilter, Ordered {

    @Autowired
    private TianxuanGatewayExcludeConfig tianxuanGatewayExcludeConfig;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private JWTUtils jwtUtils;

    public static final String HEADER_USER_TOKEN = "headerUserToken";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> excludeUrls = tianxuanGatewayExcludeConfig.getUrls();

        String url = exchange.getRequest().getURI().getPath();

        excludeUrls.add("/tianxuan-auth/adminPassport/login");
        if (CollectionUtils.isNotEmpty(excludeUrls)) {
            for (String excludeUrl : excludeUrls) {
                if (antPathMatcher.matchStart(excludeUrl, url)) {
                    return chain.filter(exchange);
                }
            }
        }
        log.info("被拦截了...");


        // 判断header中是否有token，对用户请求进行判断拦截
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String userToken = headers.getFirst(HEADER_USER_TOKEN);

        // 判空header中的令牌
        if (StringUtils.isNotBlank(userToken)) {
            String[] tokenArr = userToken.split(JWTUtils.AT);
            if (tokenArr.length < 2) {
                return renderErrorMsg(exchange, ResponseStatusEnum.UN_LOGIN);
            }

            // 获得jwt的令牌与前缀
            String prefix = tokenArr[0];
            String jwt = tokenArr[1];

            // 判断并且处理用户信息
            if (prefix.equalsIgnoreCase(TOKEN_USER_PREFIX)) {
                return dealJWT(jwt, exchange, chain, APP_USER_JSON);
            } else if (prefix.equalsIgnoreCase(TOKEN_SAAS_PREFIX)) {
                return dealJWT(jwt, exchange, chain, SAAS_USER_JSON);
            } else if (prefix.equalsIgnoreCase(TOKEN_ADMIN_PREFIX)) {
                return dealJWT(jwt, exchange, chain, ADMIN_USER_JSON);
            }
        }

        return renderErrorMsg(exchange, ResponseStatusEnum.UN_LOGIN);
    }

    public Mono<Void> dealJWT(String jwt, ServerWebExchange exchange, GatewayFilterChain chain, String key) {
        try {
            String userJson = jwtUtils.checkJWT(jwt);
            ServerWebExchange serverWebExchange = setNewHeader(exchange, key, userJson);
            return chain.filter(serverWebExchange);
        } catch (ExpiredJwtException e) {
            log.info("jwt ExpiredJwtException error: {}", e.getMessage());
            return renderErrorMsg(exchange, ResponseStatusEnum.JWT_EXPIRE_ERROR);
        } catch (Exception e) {
            log.info("jwt error: {}", e.getMessage());
            return renderErrorMsg(exchange, ResponseStatusEnum.JWT_SIGNATURE_ERROR);
        }
    }

    private ServerWebExchange setNewHeader(ServerWebExchange exchange
            , String headerKey
            , String headerValue) {
        ServerHttpRequest newRequest = exchange.getRequest()
                .mutate()
                .header(headerKey, headerValue)
                .build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return newExchange;
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

    /**
     * 数值越小, 优先级越大
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
