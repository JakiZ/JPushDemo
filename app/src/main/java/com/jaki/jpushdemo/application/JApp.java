package com.jaki.jpushdemo.application;

import android.app.Application;

import com.jaki.jpushdemo.utils.JUtils;

/**
 * Created by Administrator on 2017/12/19.
 */

public class JApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        JUtils.setDebugMode(true);
        JUtils.init(getApplicationContext());
    }
}
