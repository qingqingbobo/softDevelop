package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

@Data
public class complainAdmin {
    private Integer id;
    private String complain_content;
    private String complain_time;
    private String complain_user;
}
