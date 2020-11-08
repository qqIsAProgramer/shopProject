package com.qyl.shop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qyl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPassword {

    private int id;
    private String encryptPassword;
    private int userId;
}
