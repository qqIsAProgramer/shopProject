package com.qyl.shop.error;

/**
 * @Author: qyl
 * @Date: 2020/11/5 20:15
 */
public interface CommonError {

    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);

}
