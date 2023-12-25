package com.wuyiccc.chat.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wuyiccc
 * @date 2023/12/25 20:31
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup boosGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            // 定义主从线程组

            // 定义主线程组, 用于接受客户端连接, 但是不做任何处理
            boosGroup = new NioEventLoopGroup();
            // 定义从线程组, 工作线程组, 用于处理客户端连接
            workerGroup = new NioEventLoopGroup();

            // 创建netty服务器
            ServerBootstrap server = new ServerBootstrap();

            // 绑定主从线程组
            server.group(boosGroup, workerGroup)
                    // 设置nio的双向通道
                    .channel(NioServerSocketChannel.class)
                    // 绑定处理器, 用于处理workerGroup的任务
                    .childHandler(new HttpServerInitializer());

            // 启动server, 同步方式运行
            ChannelFuture channelFuture = server.bind(11081).sync();

            // 监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}