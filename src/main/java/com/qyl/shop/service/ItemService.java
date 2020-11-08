package com.qyl.shop.service;

import com.qyl.shop.error.BusinessException;
import com.qyl.shop.service.model.ItemModel;

import java.util.List;

/**
 * @Author: qyl
 * @Date: 2020/11/7 0:15
 */
public interface ItemService {

    // 创建商品
    ItemModel createItem(ItemModel itemModel);

    // 商品列表浏览
    List<ItemModel> itemList();

    // 商品详情
    ItemModel getItemById(Integer id);

    // 扣减库存
    boolean decreaseStock(Integer itemId, Integer amount);

    // 对应商品下单后销量增加
    void increaseSales(Integer itemId, Integer amount);
}
