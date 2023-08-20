package com.wuyiccc.tianxuan.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 聊天信息存储表
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@ToString
public class ChatMessage {


    private String id;

    /**
     * 发送者的用户id
     */
    private String senderId;

    /**
     * 接受者的用户id
     */
    private String receiverId;

    /**
     * 消息接受者的类型，是HR还是求职者，目的为了让前端不同页面接受判断并且处理显示
     */
    private Integer receiverType;

    /**
     * 聊天内容
     */
    private String msg;

    /**
     * 消息类型，有文字类、图片类、视频类...等，详见枚举类
     */
    private Integer msgType;

    /**
     * 消息的聊天时间，既是发送者的发送时间、又是接受者的接受时间
     */
    private LocalDateTime chatTime;

    /**
     * 标记存储数据库，用于历史展示。每超过1分钟，则显示聊天时间，前端可以控制时间长短
     */
    private Integer showMsgDateTimeFlag;

    /**
     * 视频地址
     */
    private String videoPath;

    /**
     * 视频宽度
     */
    private Integer videoWidth;

    /**
     * 视频高度
     */
    private Integer videoHeight;

    /**
     * 视频时间
     */
    private Integer videoTimes;

    /**
     * 语音地址
     */
    private String voicePath;

    /**
     * 语音时长
     */
    private Integer speakVoiceDuration;

    /**
     * 语音消息标记是否已读未读，true: 已读，false: 未读
     */
    private Boolean isRead;

    /**
     * 候选人用户id
     */
    private String resumeUserId;

    /**
     * 简历名称（候选人名称）
     * 简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String resumeName;

    /**
     * 候选人职位
     * 简历名称与职位使用字段冗余，目的相当于快照，只记录当时信息
     */
    private String resumePosition;
}
