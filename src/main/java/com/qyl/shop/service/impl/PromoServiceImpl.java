package com.qyl.shop.service.impl;

import com.qyl.shop.dao.PromoMapper;
import com.qyl.shop.pojo.Promo;
import com.qyl.shop.service.PromoService;
import com.qyl.shop.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: qyl
 * @Date: 2020/11/7 18:14
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Resource
    private PromoMapper promoMapper;

    @Override
    public PromoModel getPromoById(Integer itemId) {
        Promo promo = promoMapper.selectByItemId(itemId);
        PromoModel promoModel = this.convertModelFromPromo(promo);
        if (promoModel == null) {
            return null;
        }

        // 判断秒杀活动的当前状态
        if(promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(0);
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(-1);
        } else {
            promoModel.setStatus(1);
        }

        return promoModel;
    }

    private PromoModel convertModelFromPromo(Promo promo) {
        if (promo == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promo, promoModel);
        promoModel.setStartDate(new DateTime(promo.getStartDate()));
        promoModel.setEndDate(new DateTime(promo.getEndDate()));

        return promoModel;
    }
}
