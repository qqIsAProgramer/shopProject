package com.qyl.shop.service.model;

import lombok.Data;

/**
 * @Author: qyl
 * @Date: 2020/11/7 14:21
 */
@Data
public class OrderModel {

    // 在这里id本该使用String类型，因为订单流水号需要标示时间，订单顺序，数据库表的标识（可参照淘宝）
    // 但为了演示方便只使用Integer自增id
    private String id;

    private Integer userId;

    private Integer itemId;

    private Double itemPrice;

    private Integer amount;

    private Double orderPrice;

    // 若非空，以秒杀的方式下单
    private Integer promoId;
}
