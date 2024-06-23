package com.wuyiccc.chat.websocket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import io.netty.channel.Channel;

import java.util.*;

/**
 * @author wuyiccc
 * @date 2023/12/27 20:21
 * 会话管理
 * 用户id和channel的管理关系处理
 */
public class UserChannelSession {

    // 用于多端多设备同时接受消息, 允许同一个账号在多个设备同时在线, 比如ipad与iphone都能同时收到消息
    // userId: channel
    private static Map<String, List<Channel>> multiSession = new HashMap<>();

    // 用于记录用户id和客户端longId的关联关系
    // channelId: userId
    private static Map<String, String> userChannelIdRelation = new HashMap<>();

    public static void putUserChannelIdRelation(String channelId, String userId) {
        userChannelIdRelation.put(channelId, userId);
    }


    public static String getUserIdByChannelId(String channelId) {
        return userChannelIdRelation.get(channelId);
    }

    /**
     * 建立连接后初始化用户会话
     */
    public static void putMultiChannels(String userId, Channel channel) {

        List<Channel> channels = getMultiChannels(userId);
        if (Objects.isNull(channels)) {
            channels = new ArrayList<>();
        }
        channels.add(channel);

        multiSession.put(userId, channels);
    }

    public static List<Channel> getMultiChannels(String userId) {

        return multiSession.get(userId);
    }

    public static void outputMulti() {

        System.out.println("------------------------");

        for (Map.Entry<String, List<Channel>> entry : multiSession.entrySet()) {

            System.out.println("+++++++++++++++++++++++++++++++=");

            System.out.println("userId: " + entry.getKey());

            List<Channel> temp = entry.getValue();
            for (Channel c : temp) {
                System.out.println("\t\tchannelId: " + c.id().asLongText());
            }

            System.out.println("+++++++++++++++++++++++++++++++=");

        }


        System.out.println("------------------------");


    }

    /**
     * 获得我的设备其他端的channel, 用户在我发送消息的时候，进行同步给其他设备
     */
    public static List<Channel> getMyOtherChannels(String userId, String channelId) {

        List<Channel> channels = getMultiChannels(userId);

        if (CollUtil.isEmpty(channels)) {
            return ListUtil.empty();
        }

        List<Channel> myOtherChannels = new ArrayList<>();
        for (Channel tmpChannel : channels) {
            if (!tmpChannel.id().asLongText().equals(channelId)) {
                myOtherChannels.add(tmpChannel);
            }
        }

        return myOtherChannels;
    }

    /**
     * 移除多余的无用channel
     */
    public static void removeUselessChannel(String channelId, String userId) {

        List<Channel> channels = getMultiChannels(userId);
        if (CollUtil.isEmpty(channels)) {
            return;
        }

        Iterator<Channel> iterator = channels.iterator();
        while (iterator.hasNext()) {
            Channel channel = iterator.next();
            if (channel.id().asLongText().equals(channelId)) {
                iterator.remove();
            }
        }

        if (CollUtil.isEmpty(channels)) {
            multiSession.remove(userId);
        }
        userChannelIdRelation.remove(channelId);
    }
}
