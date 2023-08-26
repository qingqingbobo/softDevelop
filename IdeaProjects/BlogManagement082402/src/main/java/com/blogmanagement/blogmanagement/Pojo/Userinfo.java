package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Userinfo {
    private Integer userid;
    private String name;
    private String avatart;
    private String password;
    private String province;
    private String city;
    private String gender;
    private java.sql.Timestamp create;
    private Boolean valid;
    private String role;
    private String signature;
    private String phonenum;
    private Integer id;
}
