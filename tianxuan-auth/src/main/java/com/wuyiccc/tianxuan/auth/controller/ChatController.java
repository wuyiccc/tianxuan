package com.wuyiccc.tianxuan.auth.controller;

import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2024/6/23 17:36
 */
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Resource
    private RedisUtils redisUtils;


    @PostMapping("/getMyUnReadCount")
    public R<Map<Object, Object>> getMyUnReadCount(@RequestParam String myId) {

        Map<Object, Object> msgCache = redisUtils.hgetall(BaseInfoProperties.CHAT_MSG_LIST + StrPool.COLON + myId);

        return R.ok(msgCache);
    }

    @PostMapping("/clearMyUnReadCount")
    public R<String> clearMyUnReadCount(@RequestParam String myId, @RequestParam String oppositeId) {

        redisUtils.setHashValue(BaseInfoProperties.CHAT_MSG_LIST + StrPool.COLON + myId, oppositeId, "0");
        return R.ok();
    }
}
