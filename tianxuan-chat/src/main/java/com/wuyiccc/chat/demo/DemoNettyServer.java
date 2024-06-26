package com.wuyiccc.chat.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

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
                }).bind(8000);
    }
}
