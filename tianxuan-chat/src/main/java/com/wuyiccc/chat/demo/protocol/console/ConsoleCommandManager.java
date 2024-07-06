package com.wuyiccc.chat.demo.protocol.console;

import com.wuyiccc.chat.demo.protocol.console.impl.CreateGroupConsoleCommand;
import com.wuyiccc.chat.demo.protocol.console.impl.LogoutConsoleCommand;
import com.wuyiccc.chat.demo.protocol.console.impl.SendToUserConsoleCommand;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.Channel;

import java.util.*;

/**
 * @author wuyiccc
 * @date 2024/7/6 21:13
 */
public class ConsoleCommandManager implements ConsoleCommand {


    private Map<String, ConsoleCommand> commandCache;

    public ConsoleCommandManager() {

        commandCache = new HashMap<>();
        commandCache.put("sendToUser", new SendToUserConsoleCommand());
        commandCache.put("logout", new LogoutConsoleCommand());
        commandCache.put("createGroup", new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {

        String command = scanner.next();

        if (!SessionUtils.hasLogin(channel)) {
            return;
        }

        ConsoleCommand consoleCommand = commandCache.get(command);

        if (Objects.nonNull(consoleCommand)) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别 [" + command + "] 指令，请重新输入!");
        }
    }
}
