package com.qyl.shop.service.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: qyl
 * @Date: 2020/11/6 15:19
 */
@Data
public class UserModel {

    private int id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄不能小于0岁")
    @Max(value = 150, message = "年龄不能大于150岁")
    private int age;

    @NotBlank(message = "手机号不能为空")
    private String telephone;

    private String registerMode;
    private String thirdPartyId;

    @NotBlank(message = "密码不能为空")
    private String encryptPassword;
}
