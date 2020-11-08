package com.qyl.shop.response;

import lombok.Data;

/**
 * @Author: qyl
 * @Date: 2020/11/5 19:53
 */

@Data
public class CommonReturnType {

    private String status;
    private Object data;

    public static CommonReturnType create(Object data) {
        return CommonReturnType.create(data, "SUCCESS");
    }

    public static CommonReturnType create(Object data, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(data);
        return type;
    }

}
