package com.qyl.shop.dao;

import com.qyl.shop.pojo.Promo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: qyl
 * @Date: 2020/11/7 18:19
 */
@Mapper
public interface PromoMapper {

    Promo selectByItemId(Integer itemId);
}
