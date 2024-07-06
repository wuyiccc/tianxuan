package com.wuyiccc.chat.demo.protocol.console.impl;

import com.wuyiccc.chat.demo.protocol.console.ConsoleCommand;
import com.wuyiccc.chat.demo.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wuyiccc
 * @date 2024/7/6 22:30
 */
public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("发送消息给某个用户: ");

        String toUserId = scanner.next();
        String message = scanner.next();

        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
