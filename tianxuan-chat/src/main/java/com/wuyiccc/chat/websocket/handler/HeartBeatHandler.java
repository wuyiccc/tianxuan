package com.wuyiccc.chat.websocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.data.annotation.Id;

/**
 * @author wuyiccc
 * @date 2023/12/28 21:53
 * 用于检测channel心跳的处理器
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        // 判断event是否是用户的空闲状态事件 (空闲事件状态: 读空闲, 写空闲, 读写空闲)
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("进入读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("进入写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("进入读写空闲, channel关闭前的数量为: " + ChatHandler.clients.size());
                Channel channel = ctx.channel();
                // 关闭无用的channel
                channel.close();
                System.out.println("channel关闭后, clients的数量为: " + ChatHandler.clients.size());
            }
        }

    }
}
