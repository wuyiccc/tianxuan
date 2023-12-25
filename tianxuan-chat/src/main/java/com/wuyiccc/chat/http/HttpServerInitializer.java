package com.wuyiccc.chat.http;

import com.wuyiccc.chat.http.handler.HttpHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author wuyiccc
 * @date 2023/12/25 20:49
 * 初始化器, channel注册后, 会执行里面的相应的初始化方法
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {


        ChannelPipeline pipeline = socketChannel.pipeline();

        // 当请求到达服务端, 需要做解码，响应给到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 注册(添加) 自定义的助手类, 返回 hello netty
        pipeline.addLast("httpHandler", new HttpHandler());

    }
}
