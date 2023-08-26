package com.example.demo0814.Pojo;

import lombok.Data;

// wrap the return data
@Data
public class Result {
    // state code
    private Integer code; // 200-success, 201-failure
    // data
    private Object result; // conserve the data
}
