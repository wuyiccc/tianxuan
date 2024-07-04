package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyiccc
 * @date 2024/7/4 20:30
 */
public class TestPackageReceiveHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(DateUtil.date() + ": 服务端接收到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }
}
