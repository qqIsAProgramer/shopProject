package com.qyl.shop.pojo;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @Author: qyl
 * @Date: 2020/11/7 18:13
 */
@Data
public class Promo {

    private Integer id;

    // 秒杀活动名称
    private String promoName;

    // 活动开始时间
    private Date startDate;

    // 活动结束时间
    private Date endDate;

    // 参加秒杀活动的商品
    private Integer itemId;

    // 秒杀的价格
    private Double promoItemPrice;
}
