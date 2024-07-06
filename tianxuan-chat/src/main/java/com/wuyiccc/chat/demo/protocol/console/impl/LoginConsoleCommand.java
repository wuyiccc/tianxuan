package com.wuyiccc.chat.demo.protocol.console.impl;

import com.wuyiccc.chat.demo.protocol.console.ConsoleCommand;
import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyiccc
 * @date 2024/7/6 21:06
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("请输入登录用户名: ");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername(scanner.nextLine());

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);


        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
