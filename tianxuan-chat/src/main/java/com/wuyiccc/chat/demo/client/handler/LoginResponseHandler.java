package com.wuyiccc.chat.demo.client.handler;

import cn.hutool.core.date.DateUtil;
import com.wuyiccc.chat.demo.protocol.response.LoginResponsePacket;
import com.wuyiccc.chat.demo.session.Session;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/3 23:00
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {

        String userId = msg.getUserId();
        String userName = msg.getUserName();

        // 校验登录
        if (msg.isSuccess()) {
            System.out.println("[" + userName + "] 登录成功, userId为: " + msg.getUserId());
            SessionUtils.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println(DateUtil.date() + ": 客户端登录失败, 原因: " + msg.getReason());
        }
    }


}
