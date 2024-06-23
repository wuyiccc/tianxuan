package com.wuyiccc.tianxuan.pojo.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2023/12/27 20:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsg {

    private String msgId;

    // 发送者的用户id
    private String senderId;

    // 接受者的用户id
    private String receiverId;

    // 消息接受者的类型, 是HR还是求职者
    private Integer receiverType;

    // 聊天内容
    private String msg;

    // 消息类型
    /**
     * @see com.wuyiccc.tianxuan.common.enumeration.MsgTypeEnum
     */
    private Integer msgType;

    // 消息的聊天时间, 既是发送者的发送时间, 又是接受者的接收时间
    private LocalDateTime chatTime;

    // 标记存储数据库, 用于历史展示, 每超过1分钟, 则显示聊天时间
    private Integer showMsgDatetimeFlag;


    // 视频地址
    private String videoPath;

    // 视频宽度
    private Integer videoWidth;


    // 视频高度
    private Integer videoHeight;


    // 视频时间
    private Integer videoTimes;


    // 语音地址
    private String voicePath;


    // 语音时长
    private Integer speakVoiceDuration;

    // 语音标记消息是否已读未读, true 已读 false 未读
    private Boolean isRead;

    // 候选人用户id
    private String resumeUserId;

    // 简历名称(候选人名称)
    private String resumeName;

    // 候选人职位
    private String resumePosition;


    // 用于标记当前接受者是否在线
    private Boolean isReceiverOnLine;

}
