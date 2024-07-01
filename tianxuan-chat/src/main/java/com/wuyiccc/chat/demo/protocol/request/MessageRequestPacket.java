package com.wuyiccc.chat.demo.protocol.request;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import lombok.Data;

/**
 * @author wuyiccc
 * @date 2024/7/1 23:28
 */
@Data
public class MessageRequestPacket extends Packet {


    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
