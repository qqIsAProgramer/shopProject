package com.qyl.shop.dao;

import com.qyl.shop.pojo.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: qyl
 * @Date: 2020/11/7 0:31
 */
@Mapper
public interface ItemMapper {

    // insertSelective
    int addItemSelective(Item item);

    // itemList
    List<Item> itemList();

    // selectById
    Item getItemById(Integer id);

    int increaseSales(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}
