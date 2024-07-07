package com.wuyiccc.chat.demo.protocol.response;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import com.wuyiccc.chat.demo.session.Session;
import lombok.Data;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/7/7 09:48
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
