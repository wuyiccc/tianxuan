package com.wuyiccc.chat.demo;

import cn.hutool.socket.nio.NioServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author wuyiccc
 * @date 2024/6/26 21:59
 */
public class DemoNettyServer {

    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        //serverBootstrap
        //        .group(boss, worker)
        //        .channel(NioServerSocketChannel.class)
        //        .childHandler(new ChannelInitializer<NioSocketChannel>() {
        //            protected void initChannel(NioSocketChannel ch) {
        //                ch.pipeline().addLast(new StringDecoder());
        //                ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
        //                    @Override
        //                    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        //                        System.out.println(msg);
        //                    }
        //                });
        //            }
        //        })
        //        .bind(8000);

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                // NioServerSocketChannel服务端自定义一些属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                // 给每条链接指定自定义属性
                .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                // childOption给每条channel指定自定义的属性
                // 是否开启tcp底层的心跳机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 是否开启nagle算法, true关闭, false开启, 要求高实时性就关闭, 如果需要减少发送次数，减少网络交互次数就开启
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 给服务端channel设置一些属性, 设置系统用于临时存放已完成三次握手的请求的队列的最大长度, 如果链接建立频繁, 服务器处理创建新链接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 指定在服务端启动过程中的一些逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {

                        System.out.println("服务端启动中");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

                                System.out.println(msg);
                            }
                        });
                    }
                });

        bind(serverBootstrap, 11081);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {

        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {

                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功");
                } else {
                    System.out.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
