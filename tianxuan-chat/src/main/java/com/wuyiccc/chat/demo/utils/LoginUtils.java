package com.wuyiccc.chat.demo.utils;

import com.wuyiccc.chat.demo.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/7/1 23:52
 */
public class LoginUtils {


    public static void markAsLogin(Channel channel)  {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {

        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return Objects.nonNull(loginAttr.get());
    }
}
