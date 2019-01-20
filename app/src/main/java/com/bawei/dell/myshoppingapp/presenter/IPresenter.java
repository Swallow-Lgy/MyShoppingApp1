package com.bawei.dell.myshoppingapp.presenter;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IPresenter  {
    void requestDataPpost(String url, Map<String,String>map,Class clazz);
    void requestDataPget(String url,Class clazz);
    void requestDataPdelelte(String url,Class clazz);
    void requestDataPput(String url, Map<String,String>map,Class clazz);
    void requestDataPpostFile(String url,Map<String,String>map,Class clazz);
    void requestDataPduoContext(String url, Map<String,String>map, List<File> list,Class clazz);
}
