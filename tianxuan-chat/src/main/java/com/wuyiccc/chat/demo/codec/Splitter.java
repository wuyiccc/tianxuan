package com.wuyiccc.chat.demo.codec;

import com.wuyiccc.chat.demo.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author wuyiccc
 * @date 2024/7/4 20:53
 */
public class Splitter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;

    private static final int LENGTH_FIELD_LENGTH = 4;

    public Splitter() {

        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议的客户端
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            System.out.println("检测到异常连接，关闭异常客户端");
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
