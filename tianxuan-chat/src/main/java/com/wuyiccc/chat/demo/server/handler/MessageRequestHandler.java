package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.date.DateUtil;
import com.wuyiccc.chat.demo.protocol.request.MessageRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.MessageResponsePacket;
import com.wuyiccc.chat.demo.session.Session;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.lettuce.core.cluster.pubsub.api.async.PubSubAsyncNodeSelection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/7/3 22:56
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {


        // 1. 拿到消息发送方的用户信息
        Session session = SessionUtils.getSession(ctx.channel());

        // 2. 通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3. 获取接收方的用户信息
        String toUserId = messageRequestPacket.getToUserId();
        Channel receiveUserChannel = SessionUtils.getChannel(toUserId);

        if (Objects.nonNull(receiveUserChannel) && SessionUtils.hasLogin(receiveUserChannel)) {
            receiveUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败");
        }

    }
}
