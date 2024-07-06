package com.wuyiccc.chat.demo.protocol.response;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import lombok.Data;

/**
 * @author wuyiccc
 * @date 2024/7/6 23:32
 */
@Data
public class JoinGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return Command.JOIN_GROUP_RESPONSE;
    }
}
