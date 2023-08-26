package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

@Data

public class Comment {

  private long commentid;
  private long postid;
  private long author;
  private java.sql.Timestamp create;
  private String content;
  private long valid;
  private long preid;

}
