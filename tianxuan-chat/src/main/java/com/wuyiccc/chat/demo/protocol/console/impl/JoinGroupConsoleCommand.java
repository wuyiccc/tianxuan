package com.wuyiccc.chat.demo.protocol.console.impl;

import com.wuyiccc.chat.demo.protocol.console.ConsoleCommand;
import com.wuyiccc.chat.demo.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wuyiccc
 * @date 2024/7/6 23:28
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {


        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        System.out.println("输入 groupId, 加入群聊: ");
        String groupId = scanner.next();

        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
