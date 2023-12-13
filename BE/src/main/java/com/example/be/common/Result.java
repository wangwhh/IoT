package com.example.be.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(10000, "success", data);
    }

    public static <T> Result<T> success() {
        return new Result<T>(10000, "success", null);
    }

    public static <T> Result<T> success(String message) {
        return new Result<T>(10000, message, null);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<T>(10000, message, data);
    }

    public static <T> Result<T> error() {
        return new Result<T>(20000, "error", null);
    }

    public static <T> Result<T> error(Integer code) {
        return new Result<T>(code, "error", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>(20000, message, null);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<T>(code, message, null);
    }
}
