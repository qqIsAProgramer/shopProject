package com.qyl.shop.service;

import com.qyl.shop.error.BusinessException;
import com.qyl.shop.service.model.OrderModel;

/**
 * @Author: qyl
 * @Date: 2020/11/7 14:37
 */
public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
