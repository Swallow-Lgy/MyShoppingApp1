package com.bawei.dell.myshoppingapp.model;

import com.bawei.dell.myshoppingapp.callback.MyCallBack;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IModel {
    void requestDataMpost(String url, Map<String,String>map,Class clazz,MyCallBack callBack);
    void requestDataMget(String url,Class clazz,MyCallBack callBack);
    void requestDataMgdelete(String url,Class clazz,MyCallBack callBack);
    void requestDataMput(String url,Map<String,String>map,Class clazz,MyCallBack callBack);
    void requestDataMpostFile(String url,Map<String,String>map,Class clazz,MyCallBack callBack);
    void requestDataMduoContext(String url, Map<String,String>map, List<File> list,Class clazz,MyCallBack callBack);
}
