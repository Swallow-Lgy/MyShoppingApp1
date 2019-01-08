package com.bawei.dell.myshoppingapp.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;

import com.bawei.dell.myshoppingapp.app.MyApp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager{
    private final String BASE_URL="http://mobile.bwstudent.com/small/";
    private static RetrofitManager mRetrofitManager;
    public static synchronized  RetrofitManager getInstance(){
        if (mRetrofitManager==null){
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }
    private BaseApis mBaseApis;
    public RetrofitManager(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(15,TimeUnit.SECONDS);
        builder.writeTimeout(15,TimeUnit.SECONDS);
        builder.connectTimeout(15,TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                SharedPreferences preferences = MyApp.getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
                String userId = preferences.getString("userId", "");
                String sessionId = preferences.getString("sessionId", "");
                Request.Builder builder1 = request.newBuilder();
                builder1.method(request.method(),request.body());

                if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                    builder1.addHeader("userId",userId);
                    builder1.addHeader("sessionId",sessionId);
                }

                Request build = builder1.build();

                return chain.proceed(build);
            }
        });
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        mBaseApis = retrofit.create(BaseApis.class);
    }

      //可以这样生成Map<String, RequestBody> requestBodyMap
     // Map<String, String> requestDataMap这里面放置上传数据的键值对。
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }
    //get请求
    public RetrofitManager get(String url,HttpListener listener){
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return mRetrofitManager;

    }

    //delete请求
    public RetrofitManager delete(String url,HttpListener listener){
        mBaseApis.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return mRetrofitManager;

    }
    //表单post请求
    public  RetrofitManager postFormBoy(String url,Map<String,RequestBody>map,HttpListener listener){
        if (map!=null){
            map=new HashMap<>();
        }
        mBaseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
         return mRetrofitManager;
    }
    //put请求
    public RetrofitManager put(String url,Map<String,String>map,HttpListener listener){
        if (map==null){
            map = new HashMap<>();
        }
        mBaseApis.put(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return mRetrofitManager;
    }
    //普通post请求
    public RetrofitManager post(String url,Map<String,String>map,HttpListener listener){
      if (map==null){
          map = new HashMap<>();
      }
      mBaseApis.post(url,map)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(getObserver(listener));
      return mRetrofitManager;
    }
    //观察者
    private Observer getObserver(final HttpListener listener) {
        Observer  observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("TAG",e.getLocalizedMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (listener != null) {
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }

        };
        return observer;

    }
    public interface HttpListener{
        void onSuccess(String data);
        void onFail(String error);
    }

}
