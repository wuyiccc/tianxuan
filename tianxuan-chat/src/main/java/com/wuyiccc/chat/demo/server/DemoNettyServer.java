package com.wuyiccc.chat.demo.server;

import com.wuyiccc.chat.demo.codec.PacketCodecHandler;
import com.wuyiccc.chat.demo.codec.PacketDecoder;
import com.wuyiccc.chat.demo.codec.PacketEncoder;
import com.wuyiccc.chat.demo.codec.Splitter;
import com.wuyiccc.chat.demo.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuyiccc
 * @date 2024/6/26 21:59
 *
 * ctx.channel().writeAndFlush()的传播路径是从pipeline链表中最后一个out开始向前输出,
 * ctx.writeAndFlush() 事件传播路径是从pipeline的当前节点向前输出
 */
public class DemoNettyServer {

    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        DefaultEventExecutorGroup businessGroup = new DefaultEventExecutorGroup(
                8,
                new ThreadFactory() {

                    private AtomicInteger threadIndex = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "NettyServerCodecThread_" + this.threadIndex.incrementAndGet());
                    }
                });

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
                        //
                        //ch.pipeline().addLast(new StringDecoder());
                        //ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                        //    @Override
                        //    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                        //
                        //        System.out.println(msg);
                        //    }
                        //});
                        //ch.pipeline().addLast(new FirstServerHandler());

                        // inbound链的尾端链接outbound链表的尾端
                        //ch.pipeline().addLast(new InBoundHandlerA());
                        //ch.pipeline().addLast(new InBoundHandlerB());
                        //ch.pipeline().addLast(new InBoundHandlerC());
                        //
                        //ch.pipeline().addLast(new OutBoundHandlerA());
                        //ch.pipeline().addLast(new OutBoundHandlerB());
                        //ch.pipeline().addLast(new OutBoundHandlerC());

                        // ia -> ib -> ic -> oc -> ob -> oa
                        /**
                         * InBoundHandlerA: PooledUnsafeDirectByteBuf(ridx: 0, widx: 113, cap: 2048)
                         * InBoundHandlerB: PooledUnsafeDirectByteBuf(ridx: 0, widx: 113, cap: 2048)
                         * InBoundHandlerC: PooledUnsafeDirectByteBuf(ridx: 0, widx: 113, cap: 2048)
                         * OutBoundHandlerC: PooledUnsafeDirectByteBuf(ridx: 0, widx: 113, cap: 2048)
                         * OutBoundHandlerB: PooledUnsafeDirectByteBuf(ridx: 0, widx: 113, cap: 2048)
                         * OutBoundHandlerA: PooledUnsafeDirectByteBuf(ridx: 0, widx: 113, cap: 2048)
                         */
                        //ch.pipeline().addLast(new LifeCycleTestHandler());
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        // 心跳检测不需要auth认证
                        ch.pipeline().addLast(businessGroup, HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                        //ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
                        //ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
                        //ch.pipeline().addLast(LogoutRequestHandler.INSTANCE);
                        //ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
                        //ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
                        //ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
                        //ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
                        //
                        // 根据长度域进行拆包, 长度域的偏移量为0，长度域占4byte
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
                        //ch.pipeline().addLast(new TestPackageReceiveHandler());
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
