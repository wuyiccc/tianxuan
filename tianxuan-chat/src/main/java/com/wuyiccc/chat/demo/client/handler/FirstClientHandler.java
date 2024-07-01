package com.wuyiccc.chat.demo.client.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.wuyiccc.chat.demo.protocol.Packet;
import com.wuyiccc.chat.demo.protocol.PacketCodeC;
import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bouncycastle.util.Pack;

/**
 * @author wuyiccc
 * @date 2024/6/29 10:19
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {


    // 在客户端连接建立成功被调用该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(DateUtil.date() + "客户端写出数据");


        // 创建登录对象
        LoginRequestPacket login = new LoginRequestPacket();
        login.setUserId(IdUtil.simpleUUID());
        login.setUsername("wuyiccc");
        login.setPassword("wuyicccpwd");

        // 编码
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(login);

        // 写入数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 读取登录响应消息
        ByteBuf byteBuf = (ByteBuf) msg;

        // 解码数据
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        // 转为响应体对象
        if (packet instanceof LoginResponsePacket) {

            // 检查是否登录成功
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;

            if (responsePacket.isSuccess()) {
                System.out.println(DateUtil.date() + ": 客户端登录成功");
            } else {
                System.out.println(DateUtil.date() + ": 客户端登录失败");
            }
        } else {
            System.out.println("未知响应消息");
        }

    }

}
