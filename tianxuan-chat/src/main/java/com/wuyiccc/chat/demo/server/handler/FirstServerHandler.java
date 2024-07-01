package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.date.DateUtil;
import com.wuyiccc.chat.demo.protocol.Packet;
import com.wuyiccc.chat.demo.protocol.PacketCodeC;
import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import com.wuyiccc.chat.demo.protocol.request.MessageRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.LoginResponsePacket;
import com.wuyiccc.chat.demo.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wuyiccc
 * @date 2024/6/29 10:35
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    // 在接受到客户端发来的数据之后被回调
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;


        // 解码数据
        Packet requestPacket = PacketCodeC.INSTANCE.decode(buf);
        if (requestPacket instanceof LoginRequestPacket) {

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) requestPacket;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(loginRequestPacket.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(DateUtil.date() + ": 登录成功!");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号或密码错误");
                System.out.println(DateUtil.date() + ": 登录失败!");
            }
            ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseBuf);
        } else if (requestPacket instanceof MessageRequestPacket) {

            // 客户端发来消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) requestPacket;

            // 构建返回消息体
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

            System.out.println(DateUtil.date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            messageResponsePacket.setMessage("服务端回复: " + messageRequestPacket.getMessage());
            // 编码消息
            ByteBuf writeByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(writeByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

}
