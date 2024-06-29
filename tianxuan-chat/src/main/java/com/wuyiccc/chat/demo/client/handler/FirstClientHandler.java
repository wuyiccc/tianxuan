package com.wuyiccc.chat.demo.client.handler;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyiccc
 * @date 2024/6/29 10:19
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {


    // 在客户端连接建立成功被调用该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(DateUtil.date() + "客户端写出数据");

        // 1. 获取数据
        ByteBuf buffer = getByteBuf(ctx);

        // 写数据
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        // 1. 获取二进制抽象ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        byte[] bytes = "你好, wuyiccc".getBytes(StandardCharsets.UTF_8);

        // 填充数据到ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(DateUtil.date() + ": 客户端读取到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }
}
