package com.wuyiccc.tianxuan.api.task;

import com.wuyiccc.tianxuan.common.util.DingDingMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2023/12/13 21:53
 */
@Slf4j
@Component
public class AsyncTask {

    @Resource
    private DingDingMsgUtils dingDingMsgUtils;

    @Async
    public void sendSMSCode(String code) {
        dingDingMsgUtils.sendSMSCode(code);
        log.info("异步任务运行结束...");
    }
}
