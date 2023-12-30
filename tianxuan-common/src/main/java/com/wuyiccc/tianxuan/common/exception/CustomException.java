package com.wuyiccc.tianxuan.common.exception;

import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:54
 */
public class CustomException extends RuntimeException {

    private Integer status;

    private String msg;




    public CustomException(ResponseStatusEnum responseStatusEnum) {

        this.status = responseStatusEnum.status();
        this.msg = responseStatusEnum.msg();
    }

    public CustomException(String errorMsg) {
        this.status = ResponseStatusEnum.FAILED.status();
        this.msg = errorMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
