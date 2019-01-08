package com.bawei.dell.myshoppingapp.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class MyApp extends Application {
    //绘制页面时参照的设计图宽度
    public final static float DESIGN_WIDTH = 750;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        //沉浸式状态栏
        immersive();
        mContext = getApplicationContext();
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