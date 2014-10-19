package com.example.administrator.kui;

import android.app.Application;

/**
 * Created by Administrator on 2014/10/17.
 */
public class MyApplication extends Application {
    /**
     * 全局变量：软件要访问的域名
     */
    private String SiteUrl;

    public String getSiteUrl() {
        return this.SiteUrl;
    }

    /**
     * 保存软件要访问的域名
     * @param c
     */
    public void setSiteUrl(String c) {
        this.SiteUrl = c;
    }

    @Override
    public void onCreate() {
        SiteUrl = "";
        super.onCreate();
    }
}