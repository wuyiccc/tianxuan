package com.wuyiccc.chat.demo.utils;

import com.wuyiccc.chat.demo.attribute.Attributes;
import com.wuyiccc.chat.demo.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.Attribute;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuyiccc
 * @date 2024/7/1 23:52
 */
public class SessionUtils {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> groupCache = new ConcurrentHashMap<>();


    public static void bindSession(Session session, Channel channel) {

        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {

        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }


    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {

        groupCache.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {

        return groupCache.get(groupId);
    }
}
