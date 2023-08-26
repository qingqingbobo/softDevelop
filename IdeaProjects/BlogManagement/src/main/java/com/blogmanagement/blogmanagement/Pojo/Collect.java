package com.blogmanagement.blogmanagement.Pojo;

import lombok.Data;

@Data
public class Collect {
  private long postid;
  private long userid;
  private java.sql.Timestamp create;
}
