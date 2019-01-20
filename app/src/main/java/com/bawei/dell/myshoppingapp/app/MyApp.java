package com.bawei.dell.myshoppingapp.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MyApp extends Application {
    //绘制页面时参照的设计图宽度
    public final static float DESIGN_WIDTH = 750;
    private static Context mContext;
    private RefWatcher refWatcher;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        //沉浸式状态栏
        immersive();
        mContext = getApplicationContext();
       refWatcher =  LeakCanary.install(this);
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApp application = (MyApp) context.getApplicationContext();
        return application.refWatcher;
    }
    public static Context getApplication(){
        return mContext;
    }
    private void immersive() {
        Point mPoint = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(mPoint);
        getResources().getDisplayMetrics().xdpi = mPoint.x / DESIGN_WIDTH * 72f;
    }


}