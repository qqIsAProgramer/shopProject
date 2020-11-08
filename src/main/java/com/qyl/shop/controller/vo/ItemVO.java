package com.qyl.shop.controller.vo;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @Author: qyl
 * @Date: 2020/11/7 9:56
 */
@Data
public class ItemVO {

    private String title;

    private Double price;

    private Integer stock;

    private String description;

    private Integer sales;

    private String imgUrl;

    // 秒杀状态：0代表待抢购，1代表抢购中，-1代表无秒杀活动
    private Integer promoStatus;

    // 秒杀活动价格
    private Double promoPrice;

    // 秒杀活动id
    private Integer promoId;

    private String startDate;
}
