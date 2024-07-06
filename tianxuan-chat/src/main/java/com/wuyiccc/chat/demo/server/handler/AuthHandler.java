package com.wuyiccc.chat.demo.server.handler;

import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wuyiccc
 * @date 2024/7/6 11:34
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("开始进行认证校验");

        if (!SessionUtils.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 如果已经认证成功过, 那么删除handler
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        if (SessionUtils.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕, 无需再次验证，AuthHandler被移除");
        } else {
            System.out.println("未验证，AuthHandler被移除");
        }
    }
}
