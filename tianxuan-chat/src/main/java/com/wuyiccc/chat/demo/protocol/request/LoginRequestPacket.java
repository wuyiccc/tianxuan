package com.wuyiccc.chat.demo.protocol.request;

import com.wuyiccc.chat.demo.protocol.Packet;
import lombok.Data;

import static com.wuyiccc.chat.demo.protocol.Command.LOGIN_REQUEST;

/**
 * @author wuyiccc
 * @date 2024/6/29 17:20
 */
@Data
public class LoginRequestPacket extends Packet {


    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
