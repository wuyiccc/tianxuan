package com.wuyiccc.chat.websocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author wuyiccc
 * @date 2023/12/25 21:23
 * 处理消息的handler
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    // 用于记录和管理所有客户端的channel
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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

        // 测试向所有客户端群发消息
        //for (Channel channel : clients) {
        //    channel.writeAndFlush(replayMsg);
        //}
        clients.writeAndFlush(replayMsg);


    }

    // 客户端连接到服务端之后触发, 打开链接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        String channelId = ctx.channel().id().asLongText();
        System.out.println("客户端连接, channel对应的长id为: " + channelId);

        // 获取客户端的channel, 并放入到channelGroup中进行管理
        clients.add(ctx.channel());
    }

    // 客户端关闭连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved, channelGroup自动移除对应客户端的channel
        String channelId = ctx.channel().id().asLongText();
        System.out.println("客户端断开, channel对应的长id为: " + channelId);
        clients.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();;

        // 发生异常之后, 关闭连接channel
        ctx.channel().close();
        // 从channelGroup中移除对应的channel
        clients.remove(ctx.channel());

    }
}
