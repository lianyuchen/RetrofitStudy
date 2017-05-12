package com.demo.lianyuchen.retrofitstudy.helper;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.demo.lianyuchen.retrofitstudy.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by lianyuchen on 17/5/4.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "Converter";
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d(TAG, "返回response==：" + response);
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            JSONObject object = JSONObject.parseObject(response);
            int code = object.getIntValue("code");
//            int code = result.getCode();
            if (code == 0) {
                Log.e(TAG, "result.getData() == ：" + object);
                if (object.get("data") instanceof String){
                    throw new ApiException(code, object.getString("data"));
                }else {
                    return adapter.fromJson(response);
                }
            } else {
                Log.d(TAG, "返回err==：" + response);
//                HttpErrResult errResponse = gson.fromJson(response, HttpErrResult.class);
//                if (code == -1) {
                throw new ApiException(code, object.getString("codeMsg"));
//                }
//                return null;
            }
        } finally {
            value.close();
        }

    }
}
