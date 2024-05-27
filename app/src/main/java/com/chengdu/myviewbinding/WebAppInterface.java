package com.chengdu.myviewbinding;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    Context mContext;

    WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void log(String message) {
        Log.d("WebView", message);
    }
}
