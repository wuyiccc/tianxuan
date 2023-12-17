package com.wuyiccc.tianxuan.common.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.wuyiccc.tianxuan.common.config.TianxuanDingDingConfig;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * @author wuyiccc
 * @date 2023/7/2 08:14
 */
@Component
@Slf4j
public class DingDingMsgUtils {

    @Autowired
    private TianxuanDingDingConfig tianxuanDingDingConfig;

    //@Retryable(
    //        // 指定触发重试的异常
    //        value = CustomException.class,
    //        // 最大重试次数
    //        maxAttempts = 5,
    //        // 重试间隔为1s, 后续重试为次数的两倍
    //        // 1s, 2s, 4s, 8s ...
    //        backoff = @Backoff(delay = 1000L, multiplier = 2)
    //)
    public void sendSMSCode(String smsCode) {

//        int i = RandomUtils.nextInt(1, 5);
//
//        log.info("随机数: {}", i);
//
//        if (i <= 6) {
//            throw new CustomException("短信发送失败");
//        }

        try {
            long timestamp = System.currentTimeMillis();
            String secret = tianxuanDingDingConfig.getSecret();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac;
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");


            String url = tianxuanDingDingConfig.getRobotAccessUrl();
            url = url + "&timestamp=" + timestamp + "&sign=" + sign;

            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("本次登录验证码为: " + smsCode);
            request.setText(text);
            OapiRobotSendResponse response = client.execute(request);
            log.info("钉钉消息通知发送返回信息: {}", response.getMessage());

        } catch (Exception e) {

            log.error("钉钉消息通知发送失败: {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

    }

    // 达到最大重试次数, 或者抛出一个没有被设置 (进行重试) 的异常
    // 可以作为方法的最终兜底处理, 如果不处理也可以
    // 自动被@Retryable调用
    //@Recover
    //public void recover() {
    //    log.info("################ recover执行 ############");
    //}
}
