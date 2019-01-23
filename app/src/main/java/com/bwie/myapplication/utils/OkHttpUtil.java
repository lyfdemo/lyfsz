package com.bwie.myapplication.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {


    public static OkHttpUtil getInter() {
        return OkHttp.okHttpUtil;
    }

    static class OkHttp {
        private static final OkHttpUtil okHttpUtil = new OkHttpUtil();
    }

    public void asyncGet(String url, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
