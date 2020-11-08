package com.qyl.shop.dao;

import com.qyl.shop.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: qyl
 * @Date: 2020/11/7 14:30
 */
@Mapper
public interface OrderMapper {

    int countOrder();

    int addOrderSelective(Order order);
}
