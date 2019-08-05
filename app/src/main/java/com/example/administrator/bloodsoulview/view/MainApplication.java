package com.example.administrator.bloodsoulview.view;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class MainApplication extends Application {

    private static MainApplication myApplication = null;

    public static MainApplication getApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        Utils.init(this);
    }

}
