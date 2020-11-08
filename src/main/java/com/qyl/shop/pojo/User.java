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
public class User {

    private int id;
    private String name;
    private Integer gender;
    private int age;
    private String telephone;
    private String registerMode;
    private String thirdPartyId;
}
