package com.wuyiccc.tianxuan.common.result;

/**
 * @author wuyiccc
 * @date 2023/6/19 22:59
 */
public class CommonResult<T> {

    // 响应业务状态码
    private Integer status;

    // 响应消息
    private String msg;

    // 是否成功
    private Boolean success;

    // 响应数据
    private T data;

    /**
     * 成功返回，带有数据的，直接往OK方法丢data数据即可
     */
    public static <T> CommonResult<T> ok(T data) {
        return new CommonResult<>(data);
    }
    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入（其实就是null）
     */
    public static <T> CommonResult<T> ok() {
        return new CommonResult<T>(ResponseStatusEnum.SUCCESS);
    }
    public CommonResult(T data) {
        this.status = ResponseStatusEnum.SUCCESS.status();
        this.msg = ResponseStatusEnum.SUCCESS.msg();
        this.success = ResponseStatusEnum.SUCCESS.success();
        this.data = data;
    }


    /**
     * 错误返回，直接调用error方法即可，当然也可以在ResponseStatusEnum中自定义错误后再返回也都可以
     */
    public static <T> CommonResult<T> error() {
        return new CommonResult<>(ResponseStatusEnum.FAILED);
    }


    /**
     * 错误返回，直接返回错误的消息
     */
    public static <T> CommonResult<T> errorMsg(String msg) {
        return new CommonResult<>(ResponseStatusEnum.FAILED, msg);
    }

    public static <T> CommonResult<T> errorData(T errorData) {
        return new CommonResult<>(ResponseStatusEnum.FAILED, errorData);
    }

    /**
     * 错误返回，token异常，一些通用的可以在这里统一定义
     * @return
     */
    public static <T> CommonResult<T> errorTicket() {
        return new CommonResult<>(ResponseStatusEnum.TICKET_INVALID);
    }

    /**
     * 自定义错误范围，需要传入一个自定义的枚举，可以到[ResponseStatusEnum.java[中自定义后再传入
     * @param responseStatus
     * @return
     */
    public static <T> CommonResult<T> errorCustom(ResponseStatusEnum responseStatus) {
        return new CommonResult<>(responseStatus);
    }
    public static <T> CommonResult<T> exception(ResponseStatusEnum responseStatus) {
        return new CommonResult<>(responseStatus);
    }

    public CommonResult(ResponseStatusEnum responseStatus) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
    }
    public CommonResult(ResponseStatusEnum responseStatus, T data) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
        this.data = data;
    }
    public CommonResult(ResponseStatusEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.msg = msg;
        this.success = responseStatus.success();
    }

    public CommonResult() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
