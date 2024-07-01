package com.wuyiccc.chat.demo.protocol.response;

import com.wuyiccc.chat.demo.protocol.Command;
import com.wuyiccc.chat.demo.protocol.Packet;
import lombok.Data;

/**
 * @author wuyiccc
 * @date 2024/7/1 21:30
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
