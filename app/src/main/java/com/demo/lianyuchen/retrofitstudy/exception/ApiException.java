package com.demo.lianyuchen.retrofitstudy.exception;

/**
 * Created by lianyuchen on 17/4/26.
 */

public class ApiException extends RuntimeException {

    private int code;
    private String codeMsg;

    public ApiException(int code, String codeMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
    }

    public int getCode() {
        return code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }
}
