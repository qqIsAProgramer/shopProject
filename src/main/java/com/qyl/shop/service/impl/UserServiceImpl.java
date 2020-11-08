package com.qyl.shop.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.qyl.shop.dao.UserMapper;
import com.qyl.shop.dao.UserPasswordMapper;
import com.qyl.shop.error.BusinessException;

import com.qyl.shop.error.EnumBusinessError;
import com.qyl.shop.pojo.User;
import com.qyl.shop.pojo.UserPassword;
import com.qyl.shop.service.UserService;
import com.qyl.shop.service.model.UserModel;
import org.springframework.beans.BeanUtils;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @Author: qyl
 * @Date: 2020/11/5 23:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserPasswordMapper userPasswordMapper;

    @Override
    public UserModel getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        if(user == null) {
            return null;
        }
        UserPassword userPassword = userPasswordMapper.getPasswordById(user.getId());
        return convertModelFromPojo(user, userPassword);
    }

    @Override
    // 事务回滚注解
    @Transactional
    public void userRegister(UserModel userModel) throws BusinessException {
        if(userModel == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        User tmp = userMapper.getUserByPhone(userModel.getTelephone());
        if (tmp != null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已被注册");
        }

        // todo validate

        User user = convertPojoFromModel(userModel);
        userMapper.addUserSelective(user);

        userModel.setId(user.getId());

        UserPassword userPassword = convertPasswordFromModel(userModel);
        userPasswordMapper.insertSelective(userPassword);
    }

    @Override
    public UserModel userLogin(String telephone, String encryptPassword) throws BusinessException {
        // 通过用户手机获取用户信息
        User user = userMapper.getUserByPhone(telephone);
        if (user == null) {
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAILED);
        }
        UserPassword userPassword = userPasswordMapper.getPasswordByUserId(user.getId());
        UserModel userModel = convertModelFromPojo(user, userPassword);

        // 判断加密密码与用户密码是否一致
        if (!StringUtils.equals(encryptPassword, userModel.getEncryptPassword())) {
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAILED);
        }
        return userModel;
    }

    // 模型转换
    private User convertPojoFromModel(UserModel userModel) {
        if(userModel == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        return user;
    }

    private UserModel convertModelFromPojo(User user, UserPassword userPassword) {
        if(user == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        if(userPassword != null) {
            userModel.setEncryptPassword(userPassword.getEncryptPassword());
        }
        return userModel;
    }

    private UserPassword convertPasswordFromModel(UserModel userModel) {
        if(userModel == null) {
            return null;
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setEncryptPassword(userModel.getEncryptPassword());
        userPassword.setUserId(userModel.getId());
        return userPassword;
    }
}
