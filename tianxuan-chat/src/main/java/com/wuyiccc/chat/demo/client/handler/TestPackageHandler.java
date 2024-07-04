package com.wuyiccc.chat.demo.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyiccc
 * @date 2024/7/4 20:28
 */
public class TestPackageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 1000; i++) {
            ByteBuf buf = getByteBuf(ctx);
            System.out.println("客户端发送数据");
            ctx.channel().writeAndFlush(buf);
        }
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {


        byte[] bytes = "你好，岂曰无衣，与子同袍~".getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(bytes);

        return buf;
    }
}
