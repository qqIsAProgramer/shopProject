package com.qyl.shop.controller;

import com.qyl.shop.controller.vo.ItemVO;
import com.qyl.shop.response.CommonReturnType;
import com.qyl.shop.service.ItemService;
import com.qyl.shop.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: qyl
 * @Date: 2020/11/7 9:57
 */
@RestController
@RequestMapping("/item")
public class ItemController extends BaseController {

    @Resource
    private ItemService itemService;
    
    /*
    * 商品详情页
    * */
    @GetMapping("/get")
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = this.convertItemVOFromModel(itemModel);

        return CommonReturnType.create(itemVO);
    }

    /*
    * 创建商品
    * */
    @PostMapping("/create")
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "price") Double price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "imgUrl") String imgUrl
                                       ) {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);
        itemModel.setSales(0);

        ItemModel newItem = itemService.createItem(itemModel);
        ItemVO itemVO = this.convertItemVOFromModel(newItem);

        return CommonReturnType.create(itemVO);
    }

    /*
    * 商品列表
    * */
    @GetMapping("/list")
    public CommonReturnType itemList() {
        List<ItemModel> itemModels = itemService.itemList();
        List<ItemVO> itemVOList = itemModels.stream().map(itemModel -> {
            ItemVO itemVO = this.convertItemVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList);
    }

    /*
    * 模型转换
    * */
    private ItemVO convertItemVOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        
        // 秒杀信息
        if (itemModel.getPromoModel() != null) {
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            itemVO.setPromoStatus(-1);
        }
        
        return itemVO;
    }
}
