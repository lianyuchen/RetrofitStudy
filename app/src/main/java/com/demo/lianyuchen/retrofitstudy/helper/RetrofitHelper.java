package com.demo.lianyuchen.retrofitstudy.helper;

import com.demo.lianyuchen.retrofitstudy.constants.AppApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lianyuchen on 16/12/1.
 */

public class RetrofitHelper {

    private volatile static Retrofit retrofitInstance = null;

    public static <T> T createApi(Class<T> clazz) {
        return getInstance().create(clazz);
    }

    private static Retrofit getInstance() {
        if (null == retrofitInstance) {
            synchronized (Retrofit.class) {
                if (null == retrofitInstance) {
                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(AppApi.LOCAL_SERVER_BASE_URL)
                            .client(buildOKHttpClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofitInstance;
    }

    /**
     * 构建OKHttpClient
     *
     * @return
     */
    private static OkHttpClient buildOKHttpClient() {
        //添加日志拦截器,非debug模式不打印任何日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        loggingInterceptor.setLevel()
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)//添加日志拦截器
//                .addNetworkInterceptor()//设置网络缓存拦截器
                .cache(buildCache(new File("cache.tmp")))//设置缓存文件
                .retryOnConnectionFailure(true)//设置自动重连
                .connectTimeout(15, TimeUnit.SECONDS)//15秒连接超时
                .readTimeout(20, TimeUnit.SECONDS)//20秒读取超时
                .writeTimeout(20, TimeUnit.SECONDS)//20秒写入超时
                .build();
    }

    private static Cache buildCache(File file){
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(file, cacheSize);
        return cache;
    }
}
