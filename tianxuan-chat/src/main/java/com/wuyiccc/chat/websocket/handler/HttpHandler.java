package com.wuyiccc.chat.websocket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.springframework.http.MediaType;

/**
 * @author wuyiccc
 * @date 2023/12/25 21:23
 * 创建自定义的http服务响应处理类
 */
public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 获取channel
        Channel channel = ctx.channel();

         if (msg instanceof HttpRequest) {
             System.out.println(channel.remoteAddress());

             // 通过缓冲区定义发送的数据消息, 读写数据都是通过缓冲区进行数据交互
             ByteBuf content = Unpooled.copiedBuffer("hello netty~", CharsetUtil.UTF_8);

             // 构建http response
             FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

             // 为响应增加数据类型和长度
             response.headers().set(HttpHeaderNames.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
             response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

             ctx.writeAndFlush(response);
         }


    }
}
