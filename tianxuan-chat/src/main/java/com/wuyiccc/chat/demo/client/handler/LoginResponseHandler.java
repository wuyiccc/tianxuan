package com.wuyiccc.chat.demo.client.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.LoginResponsePacket;
import com.wuyiccc.chat.demo.utils.LoginUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;

import java.util.UUID;

/**
 * @author wuyiccc
 * @date 2024/7/3 23:00
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("wuyiccc");
        loginRequestPacket.setPassword("pwd");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {

        // 校验登录
        if (msg.isSuccess()) {
            System.out.println(DateUtil.date() + ": 客户端登录成功");
            LoginUtils.markAsLogin(ctx.channel());
        } else {
            System.out.println(DateUtil.date() + ": 客户端登录失败, 原因: " + msg.getReason());
        }
    }
}
