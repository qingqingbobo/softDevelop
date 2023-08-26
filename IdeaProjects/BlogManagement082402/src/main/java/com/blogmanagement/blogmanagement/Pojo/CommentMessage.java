package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

@Data
public class CommentMessage {
    private String avatarUrl;
    private String content;
    private String blogName;
    private String name;
    private String commentDate;
    private Integer postid;
}