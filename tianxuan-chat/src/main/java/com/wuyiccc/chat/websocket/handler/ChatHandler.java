package com.wuyiccc.chat.websocket.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.wuyiccc.chat.websocket.UserChannelSession;
import com.wuyiccc.tianxuan.common.enumeration.MsgTypeEnum;
import com.wuyiccc.tianxuan.common.util.LocalDateUtils;
import com.wuyiccc.tianxuan.pojo.netty.ChatMsg;
import com.wuyiccc.tianxuan.pojo.netty.DataContent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.Msg;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/12/25 21:23
 * 处理消息的handler
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    // 用于记录和管理所有客户端的channel
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        // 获取客户端传输过来的消息
        String content = msg.text();

        System.out.println("接受到的数据: " + content);

        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();

        //String currentChannelIdShort = currentChannel.id().asShortText();

        //System.out.println("客户端currentChannelId: " + currentChannelId);
        //System.out.println("客户端currentChannelIdShort: " + currentChannelIdShort);

        //TextWebSocketFrame replayMsg = new TextWebSocketFrame("当前客户端的id为: " + currentChannelIdShort);

        // 1. 获取客户端发来的消息 并且解析
        DataContent dataContent = JSONUtil.toBean(content, DataContent.class);
        ChatMsg chatMsg = dataContent.getChatMsg();
        String msgText = chatMsg.getMsg();
        String receiverId = chatMsg.getReceiverId();
        String senderId = chatMsg.getSenderId();

        // 时间校准, 以服务器的时间为准
        chatMsg.setChatTime(LocalDateTime.now());

        // 获得消息类型, 用于判断
        Integer msgType = chatMsg.getMsgType();

        if (MsgTypeEnum.CONNECT_INIT.type.equals(msgType)) {
            // 当websocket初次open的时候, 初始化channel会话, 把用户和channel关联起来
            UserChannelSession.putUserChannelIdRelation(currentChannelId, senderId);
            UserChannelSession.putMultiChannels(senderId, currentChannel);
        } else if (MsgTypeEnum.WORDS.type.equals(msgType)
                || MsgTypeEnum.IMAGE.type.equals(msgType)
                || MsgTypeEnum.VIDEO.type.equals(msgType)
                || MsgTypeEnum.VOICE.type.equals(msgType)) {
            // 发送消息

            // 从全局用户关系中获得对方(接受消息方)的channel
            List<Channel> multiChannels = UserChannelSession.getMultiChannels(receiverId);
            if (CollUtil.isEmpty(multiChannels)) {
                // 代表用户离线/断线状态, 消息不需要发送, 后续可以直接存储到数据库中
                chatMsg.setIsReceiverOnLine(false);
            } else {
                chatMsg.setIsReceiverOnLine(true);
                // 当channels不为空, 则同账户多端接受消息
                for (Channel c : multiChannels) {
                    Channel findChannel = clients.find(c.id());
                    if (Objects.nonNull(findChannel)) {
                        if (MsgTypeEnum.VOICE.type.equals(msgType)) {

                            chatMsg.setIsRead(false);
                        }
                        dataContent.setChatMsg(chatMsg);
                        String chatTimeFormat = LocalDateUtils.format(chatMsg.getChatTime(), LocalDateUtils.DATETIME_PATTERN_2);
                        dataContent.setChatTime(chatTimeFormat);
                        findChannel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(dataContent)));
                    }
                }
            }

            // 如果发送方在多个设备登录, 则将自己发送的消息同步到其他设备
            List<Channel> myOtherChannels = UserChannelSession.getMyOtherChannels(senderId, currentChannelId);

            for (Channel c : myOtherChannels) {

                Channel findChannel = clients.find(c.id());
                log.info("c == findChannel ? : {}", c == findChannel);
                if (Objects.nonNull(findChannel)) {
                    if (MsgTypeEnum.VOICE.type.equals(msgType)) {

                        // 语音发送对消息标记是否已经播放语音
                        chatMsg.setIsRead(false);
                    }
                    dataContent.setChatMsg(chatMsg);
                    String chatTimeFormat = LocalDateUtils.format(chatMsg.getChatTime(), LocalDateUtils.DATETIME_PATTERN_2);

                    dataContent.setChatTime(chatTimeFormat);
                    findChannel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(dataContent)));
                }


            }
        }


        UserChannelSession.outputMulti();

    }

    // 客户端连接到服务端之后触发, 打开链接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        String channelId = ctx.channel().id().asLongText();
        System.out.println("客户端连接, channel对应的长id为: " + channelId);

        // 获取客户端的channel, 并放入到channelGroup中进行管理
        clients.add(ctx.channel());
    }

    // 客户端关闭连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved, channelGroup自动移除对应客户端的channel
        String channelId = ctx.channel().id().asLongText();
        System.out.println("客户端断开, channel对应的长id为: " + channelId);
        clients.remove(ctx.channel());

        // 移除多余的会话
        String userId = UserChannelSession.getUserIdByChannelId(channelId);
        UserChannelSession.removeUselessChannel(channelId, userId);

        UserChannelSession.outputMulti();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ;

        // 发生异常之后, 关闭连接channel
        ctx.channel().close();
        // 从channelGroup中移除对应的channel
        clients.remove(ctx.channel());

        String channelId = ctx.channel().id().asLongText();

        // 移除多余的会话
        String userId = UserChannelSession.getUserIdByChannelId(channelId);
        UserChannelSession.removeUselessChannel(channelId, userId);
    }
}
