package com.wuyiccc.tianxuan.common.exception;

import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;

/**
 * @author wuyiccc
 * @date 2023/12/23 17:08
 */
public class RemoteCallCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;

    public RemoteCallCustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常状态码为: " + responseStatusEnum.status() + "异常信息为: " + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public RemoteCallCustomException(String errorMsg) {
        super(errorMsg);
        this.responseStatusEnum = ResponseStatusEnum.SYSTEM_OPERATION_ERROR;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
