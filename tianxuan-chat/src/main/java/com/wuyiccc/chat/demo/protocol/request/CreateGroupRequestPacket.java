package com.wuyiccc.chat.demo.protocol.request;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import lombok.Data;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/7/6 21:53
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;


    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
