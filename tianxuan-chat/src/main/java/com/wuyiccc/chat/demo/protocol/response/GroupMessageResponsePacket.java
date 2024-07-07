package com.wuyiccc.chat.demo.protocol.response;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import com.wuyiccc.chat.demo.session.Session;
import lombok.Data;

/**
 * @author wuyiccc
 * @date 2024/7/7 10:39
 */
@Data
public class GroupMessageResponsePacket extends Packet {


    private String fromGroupId;


    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
