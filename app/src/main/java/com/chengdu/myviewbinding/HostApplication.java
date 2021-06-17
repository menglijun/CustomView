package com.chengdu.myviewbinding;

import android.app.Application;
import android.content.Context;

/**
 * @Author liuyan
 * @Date 2021/5/17 14:27
 */
public class HostApplication extends Application {
    public static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }/*
    public static Context getContext(){
        return mContext;
    }*/
}
