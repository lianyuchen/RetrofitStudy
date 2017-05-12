package com.demo.lianyuchen.retrofitstudy.model;

/**
 * Created by lianyuchen on 17/4/26.
 */

public class HttpResult<T> {
    public int code;
    public String codeMsg;
    public String randomcode;

    public Object token;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public String getRandomcode() {
        return randomcode;
    }

    public void setRandomcode(String randomcode) {
        this.randomcode = randomcode;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return code == 0;
    }
    public boolean isFail(){
        return code == -1;
    }
}
