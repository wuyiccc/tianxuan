package com.wuyiccc.chat.websocket;

import com.wuyiccc.chat.websocket.handler.ChatHandler;
import com.wuyiccc.chat.websocket.handler.HeartBeatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author wuyiccc
 * @date 2023/12/25 20:49
 * 初始化器, channel注册后, 会执行里面的相应的初始化方法
 */
public class WSInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {


        ChannelPipeline pipeline = socketChannel.pipeline();

        // 当请求到达服务端, 需要做解码，响应给到客户端做编码
        pipeline.addLast(new HttpServerCodec());

        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // 在netty编程中都会使用到这个handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        /**
         * 上面是支持http协议的
         */


        // 增加心跳机制支持
        // 针对客户端, 如果在10分钟内没有向服务端发送读写心跳-all, 则主动断开
        // 如果读空闲或者写空闲, 则不处理
        pipeline.addLast(new IdleStateHandler(8, 10, 600));

        pipeline.addLast(new HeartBeatHandler());

        /**
         * 下面是支持websocket协议的
         */
        // websocket 服务器处理的协议, 用于指定给客户端连接访问的路由: /ws
        // 本handler会帮你处理一些繁重的事情
        // 会处理握手动作
        // 对于websocket来说, 都是以frames来进行传输, 不同的数据类型对应的frames也不通
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义处理器
        pipeline.addLast(new ChatHandler());

    }
}
