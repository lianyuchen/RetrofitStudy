package com.demo.lianyuchen.retrofitstudy.helper;

import com.demo.lianyuchen.retrofitstudy.exception.ApiException;
import com.demo.lianyuchen.retrofitstudy.model.HttpResult;

import io.reactivex.functions.Function;

/**
 * Created by lianyuchen on 17/4/26.
 */

public class BaseApi<T> implements Function<HttpResult<T>, T> {
    @Override
    public T apply(HttpResult<T> tHttpResult) throws Exception {
        if (tHttpResult.isFail()){
            throw new ApiException(tHttpResult.code, tHttpResult.codeMsg);
        }
        return tHttpResult.data;
    }
}
