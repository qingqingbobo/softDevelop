package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

@Data
public class Commentarea {
    private Integer id;
    private Integer userid;
    private String nickName;
    private String avatarUrl;
    private String content;
    private String createTime;
    private String parentId;
}
