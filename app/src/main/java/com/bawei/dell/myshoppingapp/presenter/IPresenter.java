package com.bawei.dell.myshoppingapp.presenter;

import java.util.Map;

public interface IPresenter  {
    void requestDataPpost(String url, Map<String,String>map,Class clazz);
    void requestDataPget(String url,Class clazz);
    void requestDataPdelelte(String url,Class clazz);
    void requestDataPput(String url, Map<String,String>map,Class clazz);
}
