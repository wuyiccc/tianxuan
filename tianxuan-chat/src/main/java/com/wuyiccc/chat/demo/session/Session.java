package com.wuyiccc.chat.demo.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2024/7/6 15:20
 */
@Data
@NoArgsConstructor
public class Session {

    private String userId;


    private String userName;


    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

}
