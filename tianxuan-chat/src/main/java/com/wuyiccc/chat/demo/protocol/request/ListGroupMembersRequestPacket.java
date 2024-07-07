package com.wuyiccc.chat.demo.protocol.request;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import lombok.Data;

/**
 * @author wuyiccc
 * @date 2024/7/7 09:47
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {


    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
