package com.wuyiccc.chat.demo.client.handler;

import com.wuyiccc.chat.demo.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2024/7/7 17:10
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {


    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {


        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                System.out.println("心跳包发送中...");
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
