package com.wuyiccc.chat.demo.protocol;

import lombok.Data;

/**
 * @author wuyiccc
 * @date 2024/6/29 17:03
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;


    /**
     * 指令
     */
    public abstract Byte getCommand();
}
