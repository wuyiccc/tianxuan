package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.date.DateUtil;
import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/3 22:51
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {

        System.out.println(DateUtil.date() + ": 收到客户端登录请求...");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(DateUtil.date() + ": 登录成功!");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(DateUtil.date() + ": 登录失败");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }


    private boolean valid(LoginRequestPacket loginRequestPacket) {

        return true;
    }
}
