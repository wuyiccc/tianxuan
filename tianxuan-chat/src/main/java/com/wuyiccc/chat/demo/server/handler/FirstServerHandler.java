package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyiccc
 * @date 2024/6/29 10:35
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    // 在接受到客户端发来的数据之后被回调
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;

        System.out.println(DateUtil.date() + ": 服务端读取到数据 -> " + buf.toString(StandardCharsets.UTF_8));

        // 写数据给客户端
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        // 1. 获取二进制抽象ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        byte[] bytes = "你好, 我是服务端".getBytes(StandardCharsets.UTF_8);

        // 填充数据到ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
