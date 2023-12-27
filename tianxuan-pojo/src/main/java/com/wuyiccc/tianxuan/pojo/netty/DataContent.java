package com.wuyiccc.tianxuan.pojo.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2023/12/27 20:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataContent {

    // 用户的聊天内容 netty
    private ChatMsg chatMsg;

    // 格式化后的聊天时间
    private String chatTime;

    // 扩展字段
    private String extend;
}
