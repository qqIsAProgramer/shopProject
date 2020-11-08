package com.qyl.shop.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: qyl
 * @Date: 2020/11/7 0:15
 */
@Data
public class ItemModel {

    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格要大于0")
    private Double price;

    // 库存可以为空
    private Integer stock;

    @NotBlank(message = "商品描述不能为空")
    private String description;

    private Integer sales;

    @NotBlank(message = "商品图片不能为空")
    private String imgUrl;

    // 使用聚合模型
    // 如果promoModel不为空，则代表还有未结束的秒杀活动
    private PromoModel promoModel;
}
