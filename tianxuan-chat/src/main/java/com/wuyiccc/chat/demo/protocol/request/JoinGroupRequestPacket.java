package com.wuyiccc.chat.demo.protocol.request;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import lombok.Data;

/**
 * @author wuyiccc
 * @date 2024/7/6 23:29
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
