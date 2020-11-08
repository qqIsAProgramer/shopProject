package com.qyl.shop.service.model;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * @Author: qyl
 * @Date: 2020/11/7 16:55
 */
@Data
public class PromoModel {

    private Integer id;

    // 秒杀活动状态：0表示未开始，1表示正在进行，-1表示已结束
    private Integer status;

    // 秒杀活动名称
    private String promoName;

    // 活动开始时间
    private DateTime startDate;

    // 活动结束时间
    private DateTime endDate;

    // 参加秒杀活动的商品
    private Integer itemId;

    // 秒杀的价格
    private Double promoItemPrice;
}
