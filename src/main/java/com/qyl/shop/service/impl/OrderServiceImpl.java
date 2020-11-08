package com.qyl.shop.service.impl;

import com.qyl.shop.dao.OrderMapper;
import com.qyl.shop.error.BusinessException;
import com.qyl.shop.error.EnumBusinessError;
import com.qyl.shop.pojo.Order;
import com.qyl.shop.service.ItemService;
import com.qyl.shop.service.OrderService;
import com.qyl.shop.service.UserService;
import com.qyl.shop.service.model.ItemModel;
import com.qyl.shop.service.model.OrderModel;
import com.qyl.shop.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: qyl
 * @Date: 2020/11/7 14:37
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private UserService userService;
    @Resource
    private ItemService itemService;
    @Resource
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        // 1. 校验参数：用户是否合法，下单商品是否存在，购买数量是否正确
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "商品不存在");
        }
        if(amount <= 0 || amount > 99) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不正确");
        }

        // 2. 落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result) {
            throw new BusinessException(EnumBusinessError.STOCK_NOT_ENOUGH);
        }

        // 校验活动信息
        if(promoId != null) {
            // 1. 检验活动是否在商品里
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
            } else if (itemModel.getPromoModel().getStatus().intValue() == 0) {
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "活动尚未开始");
            } else if (itemModel.getPromoModel().getStatus().intValue() == -1) {
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "活动已结束");
            }
        }

        // 3. 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        orderModel.setItemId(itemId);

        if (promoId != null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);

        // 将价格转为保留小数点后两位
        DecimalFormat df = new DecimalFormat("0.00");
        Double totalPrice = Double.valueOf(df.format(orderModel.getItemPrice().doubleValue() * amount));

        orderModel.setOrderPrice(totalPrice);
        // 生成订单流水号
        orderModel.setId(this.generateId());

        Order order = convertOrderFromModel(orderModel);
        // 4. 写入数据库
        orderMapper.addOrderSelective(order);
        // 商品销量增加
        itemService.increaseSales(itemId, amount);

        return orderModel;
    }

    // 生成订单流水号
    private String generateId() {
        StringBuilder sb = new StringBuilder();

        // 前8位为订单日期信息
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        sb.append(nowDate);
        
        // 中间6位为自增序列
        String sequence = String.valueOf(orderMapper.countOrder());
        for (int i = 0; i < 6 - sequence.length(); i++) {
            sb.append("0");
        }
        sb.append(sequence);

        // 最后两位为分库分表为，暂时写成00
        sb.append("00");

        return sb.toString();
    }

    private Order convertOrderFromModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        Order order = new Order();
        BeanUtils.copyProperties(orderModel, order);
        return order;
    }
}
