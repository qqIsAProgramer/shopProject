package com.qyl.shop.dao;

import com.qyl.shop.pojo.ItemStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: qyl
 * @Date: 2020/11/7 0:34
 */
@Mapper
public interface ItemStockMapper {

    // selectByItemId
    ItemStock selectByItemId(Integer itemId);

    // insertSelective
    int insertSelective(ItemStock itemStock);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}
