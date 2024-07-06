package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.util.IdUtil;
import com.dingtalk.api.request.OapiImpaasGroupCreateRequest;
import com.wuyiccc.chat.demo.protocol.request.CreateGroupRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.CreateGroupResponsePacket;
import com.wuyiccc.chat.demo.session.Session;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/7/6 22:09
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {

        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        // 1. 创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2. 筛选出待加入群聊用户的channel和userName
        for (String userId: userIdList) {
            Channel channel = SessionUtils.getChannel(userId);
            if (Objects.nonNull(channel)) {
                channelGroup.add(channel);
                userNameList.add(SessionUtils.getSession(channel).getUserName());
            }
        }

        // 3. 创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(IdUtil.simpleUUID());
        createGroupResponsePacket.setUserNameList(userNameList);

        // 4. 给每个客户端发送拉群通知, 利用channelGroup直接批量发送消息
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("群创建成功, id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有: " + createGroupResponsePacket.getUserNameList());

        // 保存群组相关信息
        SessionUtils.bindChannelGroup(createGroupResponsePacket.getGroupId(), channelGroup);
    }
}
