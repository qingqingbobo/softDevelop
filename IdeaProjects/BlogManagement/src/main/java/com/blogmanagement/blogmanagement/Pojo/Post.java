package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

@Data
public class Post {
    private Integer postid;
    private String title;
    private String tags;
    private String categories;
    private String content;
    private java.sql.Timestamp create;
    private long allowcomment;
    private long valid;
    private Integer userid;
    private Integer readnum;
    private Integer thumbupunm;
    private Integer commentnum;
    private Integer collectnum;
    private String thumbnall;
}
