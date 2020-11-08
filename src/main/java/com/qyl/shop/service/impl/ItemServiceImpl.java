package com.qyl.shop.service.impl;

import com.qyl.shop.dao.ItemMapper;
import com.qyl.shop.dao.ItemStockMapper;
import com.qyl.shop.error.BusinessException;
import com.qyl.shop.pojo.Item;
import com.qyl.shop.pojo.ItemStock;
import com.qyl.shop.service.ItemService;
import com.qyl.shop.service.PromoService;
import com.qyl.shop.service.model.ItemModel;
import com.qyl.shop.service.model.PromoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: qyl
 * @Date: 2020/11/7 0:28
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;
    @Resource
    private ItemStockMapper itemStockMapper;
    @Resource
    private PromoService promoService;
    
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) {
        // todo 1. 校验入参

        // 2. 模型转换
        Item item = this.convertItemFromModel(itemModel);

        // 3. 写入数据库
        itemMapper.addItemSelective(item);
        itemModel.setId(item.getId());

        ItemStock itemStock = this.convertItemStockFromModel(itemModel);
        itemStockMapper.insertSelective(itemStock);

        // 4. 返回创建完成的对象
        return itemModel;
    }

    @Override
    public List<ItemModel> itemList() {
        List<Item> items = itemMapper.itemList();
        // 很妙的方法解决了模型List转换
        List<ItemModel> itemModelList = items.stream().map(item -> {
            ItemStock itemStock = itemStockMapper.selectByItemId(item.getId());
            ItemModel itemModel = this.convertModelFromPojo(item, itemStock);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        Item item = itemMapper.getItemById(id);
        if (item == null) {
            return null;
        }
        
        // 查找库存
        ItemStock itemStock = itemStockMapper.selectByItemId(item.getId());

        // 模型转换
        ItemModel itemModel = this.convertModelFromPojo(item, itemStock);

        // 获取商品秒杀活动信息
        PromoModel promoModel = promoService.getPromoById(itemModel.getId());
        if (promoModel != null && promoModel.getStatus().intValue() != -1) {
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemStockMapper.decreaseStock(itemId, amount);
        return affectedRow > 0;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) {
        itemMapper.increaseSales(itemId, amount);
    }

    /*
    * 模型转换
    * */
    private ItemModel convertModelFromPojo(Item item, ItemStock itemStock) {
        if (item == null) {
            return null;
        }

        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item, itemModel);
        itemModel.setStock(itemStock.getStock());

        return itemModel;
    }

    private Item convertItemFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemModel, item);
        return item;
    }

    private ItemStock convertItemStockFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }

        ItemStock itemStock = new ItemStock();
        itemStock.setStock(itemModel.getStock());
        itemStock.setItemId(itemModel.getId());
        return itemStock;
    }
}
