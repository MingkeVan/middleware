package com.mw.middleware.bean;

public class ResultResponse<T> {
    private int code;
    private String message;
    private T data;

    public ResultResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ResultResponse<T> success() {
        return new ResultResponse<>(200, "Success", null);
    }

    public static <T> ResultResponse<T> success(T data) {
        return new ResultResponse<>(200, "Success", data);
    }

    public static <T> ResultResponse<T> error(int code, String message) {
        return new ResultResponse<>(code, message, null);
    }
}

