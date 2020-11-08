package com.qyl.shop.dao;

import com.qyl.shop.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: qyl
 * @Date: 2020/11/5 21:09
 */
@Mapper
public interface UserMapper {

    int deleteUser(Integer id);

    int addUser(User user);

    int addUserSelective(User user);

    User getUserById(Integer id);

    User getUserByPhone(String telephone);

    int updateUser(User user);
}
