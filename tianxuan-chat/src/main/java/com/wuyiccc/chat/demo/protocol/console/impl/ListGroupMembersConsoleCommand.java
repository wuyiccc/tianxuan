package com.wuyiccc.chat.demo.protocol.console.impl;

import com.wuyiccc.chat.demo.protocol.console.ConsoleCommand;
import com.wuyiccc.chat.demo.protocol.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wuyiccc
 * @date 2024/7/7 09:53
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {

        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();

        System.out.println("输入groupId, 获取成员列表: ");

        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
