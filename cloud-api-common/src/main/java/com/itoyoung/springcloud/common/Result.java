package com.itoyoung.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private Boolean success;

    private String code;

    private String message;

    private T data;

    public Result defaultSuccess(T data) {
        this.success = true;
        this.code = "200";
        this.message = "成功";
        this.data = data;
        return this;
    }

    public Result errorResult(String message) {
        this.success = false;
        this.code = "101";
        this.message = message;
        return this;
    }

    public Result errorResult(T data, String message) {
        this.success = false;
        this.code = "101";
        this.message = message;
        this.data = data;
        return this;
    }
}
