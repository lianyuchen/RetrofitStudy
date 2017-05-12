package com.demo.lianyuchen.retrofitstudy.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.lianyuchen.retrofitstudy.R;
import com.demo.lianyuchen.retrofitstudy.constants.AppApi;
import com.demo.lianyuchen.retrofitstudy.helper.RetrofitHelper;
import com.demo.lianyuchen.retrofitstudy.model.BaseBean;
import com.demo.lianyuchen.retrofitstudy.model.CityInfo;
import com.demo.lianyuchen.retrofitstudy.model.CityListBean;
import com.demo.lianyuchen.retrofitstudy.service.WeatherService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int OK_HTTP_REQUEST = 2;
    @BindView(R.id.button01)
    Button button01;
    @BindView(R.id.button02)
    Button button02;
    @BindView(R.id.button03)
    Button button03;
    @BindView(R.id.button04)
    Button button04;
    @BindView(R.id.button05)
    Button button05;
    @BindView(R.id.button06)
    Button button06;
    @BindView(R.id.button07)
    Button button07;
    @BindView(R.id.button08)
    Button button08;
    @BindView(R.id.button09)
    Button button09;
    @BindView(R.id.button10)
    Button button10;
    @BindView(R.id.button11)
    Button button11;
    @BindView(R.id.button12)
    Button button12;

    private Retrofit weatherRetrofit, mockyRetrofit;
    private WeatherService service, mockyService;
    private File tempFile;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private OkHttpClient client;

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    private String savePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        verifyStoragePermissions(this);
        savePath = Environment.getExternalStorageDirectory().getPath() + File.separator + getPackageName();
        File savaPathFile = new File(savePath);
        if (!savaPathFile.exists()) {
            savaPathFile.mkdirs();
        }
        Log.i(TAG, "savePath" + savePath);
        /**
         * 可以自定义client
         */
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        weatherRetrofit = new Retrofit.Builder()
                .baseUrl(AppApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mockyRetrofit = new Retrofit.Builder()
                .baseUrl(AppApi.LOCAL_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = weatherRetrofit.create(WeatherService.class);
        mockyService = mockyRetrofit.create(WeatherService.class);
    }

    @OnClick({R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05, R.id.button06, R.id.button07, R.id.button08, R.id.button09, R.id.button10, R.id.button11, R.id.button12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button01:

                Call<CityListBean> call = service.listCity("北京");
                call.enqueue(new Callback<CityListBean>() {
                    @Override
                    public void onResponse(Call<CityListBean> call, Response<CityListBean> response) {
                        if (response.isSuccessful()) {
                            CityListBean cityListBean = response.body();
                            if (null != cityListBean) {
                                for (CityListBean.RetDataBean bean : cityListBean.getRetData()) {
                                    Log.i(TAG, "area_id:" + bean.getArea_id() + " province:" + bean.getProvince_cn()
                                            + " name:" + bean.getName_cn() + " district:" + bean.getDistrict_cn());
                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CityListBean> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
                break;
            case R.id.button02:
                Call<CityInfo> cityInfoCall = mockyService.cityInfo("北京");
                cityInfoCall.enqueue(new Callback<CityInfo>() {
                    @Override
                    public void onResponse(Call<CityInfo> call, Response<CityInfo> response) {
                        if (response.isSuccessful()) {
                            CityInfo cityInfo = response.body();
                            if (null != cityInfo) {
                                Log.i(TAG, "cityCode:" + cityInfo.getRetData().getCityCode() + " cityname:" + cityInfo.getRetData().getCityName()
                                        + " province:" + cityInfo.getRetData().getProvinceName() + " TelAreaCode:" + cityInfo.getRetData().getTelAreaCode());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CityInfo> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
                break;
            case R.id.button03:
                tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                break;
            case R.id.button04:

                Call<ResponseBody> downloadCall = mockyService.downloadFile(AppApi.LOCAL_SERVER_BASE_URL + "user/photo/IMG_20160908_145410.jpg");
                downloadCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody body = response.body();
                            if (null != body) {
                                Log.i(TAG, "is download success ?"+ saveFile(body)) ;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;
            case R.id.button05:
                Call<ResponseBody> bigFileCall = mockyService.downloadFile(AppApi.LOCAL_SERVER_BASE_URL + "user/photo/55.rar");
                bigFileCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody body = response.body();
                            if (null != body) {
                                Log.i(TAG, "is download success ?"+ saveFile(body)) ;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;
            case R.id.button06:
                tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                Intent intent06 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent06.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent06, OK_HTTP_REQUEST);
                break;
            case R.id.button07:

                WeatherService weatherService = RetrofitHelper.createApi(WeatherService.class);
                Call<ResponseBody> responseBodyCall = weatherService.downloadFile(AppApi.LOCAL_SERVER_BASE_URL + "user/photo/IMG_20160908_145410.jpg");
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody body = response.body();
                            if (null != body) {
                                Log.i(TAG, "is download success ?"+ saveFile(body)) ;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
            case R.id.button08:
                RetrofitHelper.createApi(WeatherService.class)
                        .download(AppApi.LOCAL_SERVER_BASE_URL + "user/photo/IMG_20160908_145410.jpg")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new io.reactivex.Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {
                                Log.i(TAG,"onNext");
                                if (null != responseBody) {
                                    Log.i(TAG, "is download success ?"+ saveFile(responseBody)) ;
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i(TAG,"onError");
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG,"onCompleted");
                            }
                        });
                break;
            case R.id.button09:
                break;
            case R.id.button10:
                break;
            case R.id.button11:
                break;
            case R.id.button12:
                break;
        }
    }

    private boolean saveFile(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(savePath + File.separator + "HuoYunAppForMaster_release__v4.3_58585858.apk");

            InputStream inputStream = null;
            OutputStream outputStream = null;
            byte[] fileReader = new byte[4096];

            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;

            inputStream = body.byteStream();
            try {
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.i(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }


    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    /**
                     * 指定了存储位置，不会执行下面的if语句块
                     */
                    if (data != null) { //可能尚未指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        //返回有缩略图
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
//                            ivUserIcon.setImageBitmap(thumbnail);
//                            tempFile.delete();
                        }
                    } else {
                        //由于指定了目标uri，存储在目标uri，intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        // 通过目标uri，找到图片
                        // 对图片的缩放处理
                        // 操作
                        if (tempFile.exists()) {
                            // TODO: 16/9/6 执行上传
//                            startPhotoZoom(Uri.fromFile(tempFile));
                            /**
                             * 文件上传
                             * 方式一：构建RequestBody对象
                             *
                             * 方式二: 构建MultipartBody.Part对象
                             */
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);

                            MultipartBody.Part body = MultipartBody.Part.createFormData("file", tempFile.getName(), requestBody);
                            // add another part within the multipart request
                            String descriptionString = "hello, this is description speaking";
                            RequestBody description =
                                    RequestBody.create(
                                            MediaType.parse("multipart/form-data"), descriptionString);

                            RequestBody body1 = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("file", tempFile.getName(), requestBody)
                                    .build();
                            Call<BaseBean> baseBeanCall = mockyService.uploadFile(requestBody);
                            baseBeanCall.enqueue(new Callback<BaseBean>() {
                                @Override
                                public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                                    if (response.isSuccessful()) {
                                        if (null != response.body()) {
                                            Log.i(TAG, "code:" + response.body().getErrNum() + " msg:" + response.body().getErrMsg());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseBean> call, Throwable t) {
                                    Log.e(TAG, t.getMessage());
                                }
                            });
                        }

                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                    Toast.makeText(this, "取消拍照", Toast.LENGTH_LONG).show();
                }
                break;
            case OK_HTTP_REQUEST:
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    /**
                     * 指定了存储位置，不会执行下面的if语句块
                     */
                    if (data != null) { //可能尚未指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        //返回有缩略图
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
//                            ivUserIcon.setImageBitmap(thumbnail);
//                            tempFile.delete();
                        }
                    } else {
                        //由于指定了目标uri，存储在目标uri，intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        // 通过目标uri，找到图片
                        // 对图片的缩放处理
                        // 操作
                        if (tempFile.exists()) {
                            // TODO: 16/9/6 执行上传
//                            RequestBody requestBody = MultipartBody.Builder()
                            RequestBody requestBody = new MultipartBody.Builder() //建立请求的内容
                                    .setType(MultipartBody.FORM)//表单形式
                                    .addFormDataPart("file", tempFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), tempFile))//第一个参数是服务器接收的名称，第二个是上传文件的名字，第三个是上传的文件
                                    .build();
                            Request request = new Request.Builder()//建立请求
                                    .url("http://10.253.10.178:8080/ServerTool/UploadFileServlet")//请求的地址
                                    .post(requestBody)//请求的内容（上面建立的requestBody）
                                    .build();
                            client.newCall(request).enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(okhttp3.Call call, IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }

                                @Override
                                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                                    Log.i(TAG, response.body().toString());
                                }
                            });
                        }

                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                    Toast.makeText(this, "取消拍照", Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    private void startPhotoZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        // crop为true是设置在开启的intent中设置显示的view可以剪裁
//        intent.putExtra("crop", "true");
//
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//
//        // outputX,outputY 是剪裁图片的宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
//        intent.putExtra("noFaceDetection", true);
//        System.out.println("22================");
//        startActivityForResult(intent, PHOTO_REQUEST_CUT);
//    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
