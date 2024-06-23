package com.wuyiccc.tianxuan.auth.controller;

import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.auth.service.ChatMessageService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

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


    @Resource
    private ChatMessageService chatMessageService;

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

    @PostMapping("/list/{senderId}/{receiverId}")
    public R<PagedGridResult> list(@PathVariable("senderId") String senderId
            , @PathVariable("receiverId") String receiverId
            , @RequestParam Integer page
            , @RequestParam Integer pageSize) {

        if (Objects.isNull(page) || page == 0) {
            page = 1;
        }

        PagedGridResult res = chatMessageService.list(senderId, receiverId, page, pageSize);
        return R.ok(res);
    }

    @PostMapping("/signRead/{msgId}")
    public R<String> signRead(@PathVariable("msgId") String msgId) {

        chatMessageService.updateMsgSignRead(msgId);
        return R.ok();
    }
}
