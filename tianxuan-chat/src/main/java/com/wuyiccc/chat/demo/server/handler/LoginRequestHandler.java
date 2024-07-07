package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.LoginResponsePacket;
import com.wuyiccc.chat.demo.session.Session;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/3 22:51
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {


        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IdUtil.simpleUUID();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUsername() + "] 登录成功");
            SessionUtils.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);

            System.out.println(DateUtil.date() + ": 登录失败");
        }
        // 登录响应, 这种方式会从最后一个out-handler开始向前寻找, 即使解码器排在当前节点的后面
        ctx.channel().writeAndFlush(loginResponsePacket);
        // 如果解码器在LoginHandler后面, 那么这种方式直接在当前节点向前寻找out-handler, 不会走解码器
        //ctx.writeAndFlush(loginRequestPacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtils.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
