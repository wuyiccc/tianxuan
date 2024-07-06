package com.wuyiccc.chat.demo.protocol.console.impl;

import cn.hutool.core.collection.ListUtil;
import com.wuyiccc.chat.demo.protocol.console.ConsoleCommand;
import com.wuyiccc.chat.demo.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wuyiccc
 * @date 2024/7/6 21:16
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {


    private static final String USER_ID_SPLITTER = ",";


    @Override
    public void exec(Scanner scanner, Channel channel) {

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.println(" [拉入群聊] 输入 userId 列表, userId 之间英文逗号隔开: ");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIdList(ListUtil.toList(userIds.split(USER_ID_SPLITTER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
