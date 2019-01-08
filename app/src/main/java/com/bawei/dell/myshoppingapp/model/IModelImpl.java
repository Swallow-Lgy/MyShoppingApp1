package com.bawei.dell.myshoppingapp.model;

import android.util.Log;

import com.bawei.dell.myshoppingapp.callback.MyCallBack;
import com.bawei.dell.myshoppingapp.util.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel {
    //post请求
    @Override
    public void requestDataMpost(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {
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

}
