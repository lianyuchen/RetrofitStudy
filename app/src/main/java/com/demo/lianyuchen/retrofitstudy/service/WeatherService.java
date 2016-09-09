package com.demo.lianyuchen.retrofitstudy.service;

import com.demo.lianyuchen.retrofitstudy.model.BaseBean;
import com.demo.lianyuchen.retrofitstudy.model.CityInfo;
import com.demo.lianyuchen.retrofitstudy.model.CityListBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by lianyuchen on 16/9/5.
 */
public interface WeatherService {
    /**
     * 查询可用城市列表
     * <p/>
     * 请求百度天气api，使用GET方式请求，需要在Header中添加apikey
     * 这是其中一种写法,使用Headers注解。
     * @param cityname 城市名称 需要提交参数，使用Query注解
     * @return
     */
    @Headers("apikey:c114a4557cde9bb59dedd52e27707d33")
    @GET("api")
    Call<CityListBean> listCity(@Query("cityname") String cityname);
    /**
     * 这是第二种写法，将header从参数中传入
     * @param apikey
     * @param cityname
     * @return
     */
    @GET("weatherservice/citylist")
    Call<CityListBean> listCity(@Header("apikey") String apikey,
                                @Query("cityname") String cityname);
    /**
     * 第三种添加header的写法，将header放入map中，在
     * @param headers
     * @param cityname
     * @return
     */
    @GET("weatherservice/citylist")
    Call<CityListBean> listCity(@HeaderMap Map<String, String> headers,
                                @Query("cityname") String cityname);



    @Headers("apikey:c114a4557cde9bb59dedd52e27707d33")
    @GET("weatherservice/citylist")
    Call<CityListBean> listCity(@Query("cityname") String... citynames);//多个城市名

    @Headers("apikey:c114a4557cde9bb59dedd52e27707d33")
    @POST("weatherservice/citylist")
    Call<CityListBean> listCity(@QueryMap Map<String, String> cityMap);//多个参数




    /**
     * 表单类型请求，使用FormUrlEncoded注解,必须使用POST请求方式
     * <p/>
     * 在方法参数中，使用Field注解作为要上传的参数
     * <p/>
     * 一般FormUrlEncoded，Field，FieldMap 一起使用
     *
     * @param cityname
     * @return
     */

    @FormUrlEncoded
    @POST("ChineseFormServlet")
    Call<CityInfo> cityInfo(@Field("cityname") String cityname);

    @FormUrlEncoded
    @POST("v2/57cd36501200001223bb7880")
    Call<CityInfo> cityInfo(@Field("cityname") String... citynames);

    /**
     * 在方法参数中，使用FieldMap注解作为要上传的参数
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("v2/57cd36501200001223bb7880")
    Call<CityInfo> cityInfo(@FieldMap Map<String, String> params);


    /**
     * 上传文件，使用Multipart注解，通常与POST一起使用
     * 方法参数中有两种方式：
     * @Part MultipartBody.Part
     * @Part("file") RequestBody
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("UploadFileServlet")
    Call<BaseBean> uploadFile(@Part MultipartBody.Part file);

    @Multipart
    @POST("UploadFileServlet")
    Call<BaseBean> uploadFile(@Part("file") RequestBody file);

    /**
     * 多个文件上传
     * @param mapParams
     * @return
     */
    @Multipart
    @POST("UploadFileServlet")
    Call<BaseBean> uploadFile(@PartMap Map<String, RequestBody> mapParams);

    /**
     * 文件下载
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

}
