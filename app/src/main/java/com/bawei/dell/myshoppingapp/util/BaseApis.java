package com.bawei.dell.myshoppingapp.util;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;

public interface BaseApis<T> {
    @PUT
    Observable<ResponseBody> put(@Url String url,@QueryMap Map<String,String>map);
    @GET
    Observable<ResponseBody> get(@Url String url);
    @DELETE
    Observable<ResponseBody> delete(@Url String url);
    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, String> map);
    @Multipart
    @POST
    Observable<ResponseBody> postFormBody(@Url String url, @PartMap Map<String, RequestBody> requestBodyMap);
}
