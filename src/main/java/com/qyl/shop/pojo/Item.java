package com.qyl.shop.pojo;

import lombok.Data;

/**
 * @Author: qyl
 * @Date: 2020/11/7 0:01
 */
@Data
public class Item {

    private Integer id;

    private String title;

    private Double price;

    private String description;

    private Integer sales;

    private String imgUrl;
}
