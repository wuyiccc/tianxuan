package com.wuyiccc.chat.demo.attribute;

import com.wuyiccc.chat.demo.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author wuyiccc
 * @date 2024/7/1 23:53
 */
public class Attributes {

    public static AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
