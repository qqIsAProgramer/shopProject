package com.qyl.shop.error;

/**
 * @Author: qyl
 * @Date: 2020/11/5 20:21
 */

// 错误枚举类
public enum EnumBusinessError implements CommonError {

    // 通用错误类型
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),
    // 用户相关错误
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAILED(20002, "用户登录失败"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    // 订单相关
    STOCK_NOT_ENOUGH(30001, "库存不足");

    private int errCode;
    private String errMsg;

    private EnumBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
