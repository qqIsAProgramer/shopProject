package com.qyl.shop.dao;

import com.qyl.shop.pojo.UserPassword;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: qyl
 * @Date: 2020/11/6 15:48
 */
@Mapper
public interface UserPasswordMapper {

    int deleteById(Integer id);

    int insert(UserPasswordMapper userPassword);

    int insertSelective(UserPassword userPassword);

    UserPassword getPasswordById(Integer id);

    UserPassword getPasswordByUserId(Integer userId);

    int update(UserPasswordMapper userPassword);
}
