package com.wuyiccc.chat.demo.client;

import com.wuyiccc.chat.demo.client.handler.*;
import com.wuyiccc.chat.demo.codec.PacketDecoder;
import com.wuyiccc.chat.demo.codec.PacketEncoder;
import com.wuyiccc.chat.demo.codec.Splitter;
import com.wuyiccc.chat.demo.protocol.PacketCodeC;
import com.wuyiccc.chat.demo.protocol.console.ConsoleCommandManager;
import com.wuyiccc.chat.demo.protocol.console.impl.LoginConsoleCommand;
import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import com.wuyiccc.chat.demo.protocol.request.MessageRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.LogoutResponsePacket;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
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
                        //ch.pipeline().addLast(new FirstClientHandler());
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                        //ch.pipeline().addLast(new TestPackageHandler());
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
                    startConsoleThread(future.channel());
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

    private static void startConsoleThread(Channel channel) {

        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {

            while (!Thread.interrupted()) {
                if (!SessionUtils.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}


