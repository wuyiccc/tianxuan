package com.wuyiccc.chat.demo.protocol.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wuyiccc
 * @date 2024/7/6 21:02
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);
}
