package com.wuyiccc.chat.demo.client;

import com.wuyiccc.chat.demo.client.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2024/6/26 22:07
 */
public class DemoNettyClient {


    private static final Integer MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                // 指定线程模型
                .group(group)
                // 指定IO模型为NIO
                .channel(NioSocketChannel.class)
                // IO处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        //ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });

        connect(bootstrap, "127.0.0.1", 11081, MAX_RETRY);
    }


    private static void connect(Bootstrap bootstrap, String host, int port) {

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else {
                System.out.println("连接失败, 开始重连");
                connect(bootstrap, host, port);
            }
        });
    }


    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {


        bootstrap.connect(host, port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("链接成功");
                } else if (retry == 0) {
                    System.out.println("重试次数已用完, 放弃连接");
                } else {

                    // 第几次重连
                    int order = (MAX_RETRY - retry) + 1;
                    int delay = 1 << order;
                    System.err.println(new Date() + ": 链接失败, 第" + order + "次重连......");
                    // 定时间隔重连
                    bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
                }
            }
        });
    }
}


