package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

@Data
public class Complain {

  private long complainid;
  private long userid;
  private String content;
  private java.sql.Timestamp create;

}
