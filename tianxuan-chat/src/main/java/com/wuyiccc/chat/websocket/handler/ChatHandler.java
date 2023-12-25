package com.wuyiccc.chat.websocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author wuyiccc
 * @date 2023/12/25 21:23
 * 处理消息的handler
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        // 获取客户端传输过来的消息
        String content = msg.text();

        System.out.println("接受到的数据: " + content);

        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();

        String currentChannelIdShort = currentChannel.id().asShortText();

        System.out.println("客户端currentChannelId: " + currentChannelId);
        System.out.println("客户端currentChannelIdShort: " + currentChannelIdShort);

        TextWebSocketFrame replayMsg = new TextWebSocketFrame("当前客户端的id为: " + currentChannelIdShort);
        currentChannel.writeAndFlush(replayMsg);
    }
}
