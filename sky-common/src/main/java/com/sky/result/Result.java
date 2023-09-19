package com.sky.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    // 定义常见的状态码
    public static final int SUCCESS_CODE = 1;
    public static final int DEFAULT_ERROR_CODE = 0;

    private int code;
    private String msg;
    private T data; //数据

    public Result() {
        this.code = SUCCESS_CODE;
        this.msg = "Success";
    }

    public static <T> Result<T> success() {
        return new Result<T>();
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        return error(DEFAULT_ERROR_CODE, msg);
    }

    public static <T> Result<T> error(int errorCode, String errorMsg) {
        Result<T> result = new Result<>();
        result.code = errorCode;
        result.msg = errorMsg;
        return result;
    }
}
