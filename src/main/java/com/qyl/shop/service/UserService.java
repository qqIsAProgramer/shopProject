package com.qyl.shop.service;

import com.qyl.shop.error.BusinessException;
import com.qyl.shop.pojo.User;
import com.qyl.shop.service.model.UserModel;

/**
 * @Author: qyl
 * @Date: 2020/11/5 21:10
 */
public interface UserService {

    UserModel getUserById(Integer id);

    void userRegister(UserModel userModel) throws BusinessException;

    UserModel userLogin(String telephone, String password) throws BusinessException;
}
