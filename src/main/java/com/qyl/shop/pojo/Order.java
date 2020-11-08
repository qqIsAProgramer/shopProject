package com.qyl.shop.pojo;

import lombok.Data;

/**
 * @Author: qyl
 * @Date: 2020/11/7 14:29
 */
@Data
public class Order {

    private String id;

    private Integer userId;

    private Integer itemId;

    private Double itemPrice;

    private Integer amount;

    private Double orderPrice;

    // 秒杀编号
    private Integer promoId;
}
