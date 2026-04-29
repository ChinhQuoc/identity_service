package com.example.identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

// những field nào có giá trị null sẽ không được serialize ra JSON, giúp response gọn hơn
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code = 201;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
