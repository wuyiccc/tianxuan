package com.wuyiccc.chat.demo.protocol.console.impl;

import com.wuyiccc.chat.demo.protocol.console.ConsoleCommand;
import com.wuyiccc.chat.demo.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wuyiccc
 * @date 2024/7/6 22:29
 */
public class LogoutConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
