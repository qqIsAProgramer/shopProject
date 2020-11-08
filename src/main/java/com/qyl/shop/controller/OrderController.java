package com.qyl.shop.controller;

import com.qyl.shop.error.BusinessException;
import com.qyl.shop.error.EnumBusinessError;
import com.qyl.shop.response.CommonReturnType;
import com.qyl.shop.service.OrderService;
import com.qyl.shop.service.model.OrderModel;
import com.qyl.shop.service.model.UserModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: qyl
 * @Date: 2020/11/7 15:59
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    @Resource
    private HttpServletRequest httpServletRequest;

    @PostMapping("/create")
    public CommonReturnType createOrder(@RequestParam(name = "promoId", required = false) Integer promoId,
                                        @RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount
                                        ) throws BusinessException {
        // 必须要登录才可以下单
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw  new BusinessException(EnumBusinessError.USER_NOT_LOGIN, "用户还未登录，不能下单");
        }

        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel order = orderService.createOrder(userModel.getId(), itemId, promoId, amount);
        return CommonReturnType.create(order);
    }
}
