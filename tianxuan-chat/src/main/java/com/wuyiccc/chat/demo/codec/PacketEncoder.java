package com.wuyiccc.chat.demo.codec;

import com.wuyiccc.chat.demo.protocol.Packet;
import com.wuyiccc.chat.demo.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wuyiccc
 * @date 2024/7/3 22:44
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
