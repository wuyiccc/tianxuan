package com.wuyiccc.chat.demo.protocol;

/**
 * @author wuyiccc
 * @date 2024/6/29 17:19
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
