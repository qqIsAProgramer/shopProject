package com.qyl.shop.controller;

import com.alibaba.druid.util.StringUtils;
import com.qyl.shop.controller.vo.UserVO;
import com.qyl.shop.error.BusinessException;
import com.qyl.shop.error.EnumBusinessError;
import com.qyl.shop.response.CommonReturnType;
import com.qyl.shop.service.UserService;
import com.qyl.shop.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Author: qyl
 * @Date: 2020/11/5 21:15
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private HttpServletRequest httpServletRequest;

    // 注册接口
    @PostMapping("/register")
    @Transactional
    public CommonReturnType register(@RequestParam(name = "telephone") String telephone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "age") int age,
                                     @RequestParam(name = "password") String password
                                     ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 验证手机号和对应otpCode相符合
        String otpCodeInSession = (String)this.httpServletRequest.getSession().getAttribute(telephone);
        // 可以点进去看该equals的方法，会先判空
        if (!StringUtils.equals(otpCode, otpCodeInSession)) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }

        UserModel userModel = new UserModel();
        userModel.setTelephone(telephone);
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setRegisterMode("byPhone");
        // MD5加密
        userModel.setEncryptPassword(this.enCodeByMD5(password));

        userService.userRegister(userModel);

        UserVO userVO = convertVOFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    // 登录接口
    @Transactional
    @PostMapping("/login")
    public CommonReturnType login(@RequestParam(name = "telephone") String telephone,
                                  @RequestParam(name = "password") String password
                                  ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 判空指针和空白（看源码）
        if(org.apache.commons.lang3.StringUtils.isEmpty(telephone) || org.apache.commons.lang3.StringUtils.isEmpty(password)) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.userLogin(telephone, this.enCodeByMD5(password));
        // 将登录凭证加入到用户登录成功的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        System.out.println(this.httpServletRequest.getSession().getAttribute("IS_LOGIN"));

        UserVO userVO = convertVOFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    // MD5加密
    private String enCodeByMD5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密密码
        String passwordDigest = base64Encoder.encode(md5.digest(password.getBytes("utf-8")));
        return passwordDigest;
    }

    // 用户获取otp验证码
    @GetMapping("/otp")
    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone) {
        // 通过一定规则生成otp验证码
        Random random = new Random();
        // 随机生成了五位数验证码(10000 ~ 99999)
        int randomInt = random.nextInt(89999);  // 从0 ~ 89999随机生成一个数
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        // 将otp验证码与用户手机号关联（使用redis会好很多），使用http session的方式绑定
        httpServletRequest.getSession().setAttribute(telephone, otpCode);

        // 发送短信给用户（省略）
        // 用于测试，不可以直接获取用户验证码
        System.out.println("telephone = " + telephone + " & otpCode = " + otpCode);
        return CommonReturnType.create(otpCode);
    }

    // 查询一个用户
    @GetMapping("/get")
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        if(userModel == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertVOFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    private UserVO convertVOFromModel(UserModel userModel) {
        if(userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
