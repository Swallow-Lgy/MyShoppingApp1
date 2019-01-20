package com.bawei.dell.myshoppingapp.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.app.MyApp;
import com.bawei.dell.myshoppingapp.callback.MyCallBack;
import com.bawei.dell.myshoppingapp.util.RetrofitManager;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;
import java.util.Map;

public class IModelImpl implements IModel {
    //post请求
    @Override
    public void requestDataMpost(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {
         if (!isNetWork()){
             Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
         }
        RetrofitManager.getInstance().post(url,map,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Log.i("TAG",data);
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }
            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });

    }
    //get请求
    @Override
    public void requestDataMget(String url, final Class clazz, final MyCallBack callBack) {
        if (!isNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitManager.getInstance().get(url,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }

            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });
    }
    //delelt请求
    @Override
    public void requestDataMgdelete(String url, final Class clazz, final MyCallBack callBack) {
        if (!isNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitManager.getInstance().delete(url,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }

            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });
    }
    @Override
    public void requestDataMput(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {
        if (!isNetWork()){
            Toast.makeText(MyApp.getApplication(),"无可用网络",Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitManager.getInstance().put(url,map,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Log.i("TAG",data);
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }
            @Override
            public void onFail(String error) {
                callBack.setData(error);
            }
        });
    }

    @Override
    public void requestDataMpostFile(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {
        RetrofitManager.getInstance().postFile(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                callBack.setData(o);
            }

            @Override
            public void onFail(String error) {
              callBack.setData(error);
            }
        });
    }
   //图文上传
    @Override
    public void requestDataMduoContext(String url, Map<String, String> map, List<File> list, final Class clazz, final MyCallBack callBack) {
         RetrofitManager.getInstance().postduocon(url, map, list, new RetrofitManager.HttpListener() {
             @Override
             public void onSuccess(String data) {
                 Gson gson = new Gson();
                 Object o = gson.fromJson(data, clazz);
                 callBack.setData(o);
             }

             @Override
             public void onFail(String error) {
                   callBack.setData(error);
             }
         });
    }

    //判断网络状态
    public static boolean isNetWork()
    {
        ConnectivityManager cm = (ConnectivityManager) MyApp.getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isAvailable();
    }

}
