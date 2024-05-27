package com.chengdu.myviewbinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

//import com.chengdu.myviewbinding.databinding.ActivityMainBinding;
import com.chengdu.myviewbinding.databinding.ActivityMainBinding;
import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;
import com.example.qrcode.ShowResultActivity;
import com.example.qrcode.utils.CommonUtils;
import com.example.qrcode.utils.DecodeUtils;
import com.example.qrcode.utils.UriUtils;
import com.google.zxing.Result;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewBinding;
    private final int RESULT_REQUEST_CODE = 0x112;
    private final int REQUEST_CODE_GET_PIC_URI = 0x113;
    private final int PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0x114;
    private int MESSAGE_DECODE_FROM_BITMAP = 0x115;

    private final int REQUEST_PERMISION_CODE_CAMARE = 0;
//    private final int RESULT_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        mViewBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());


        initWebView();

        /*List<String> tagList = new ArrayList<>();
        String s = "啦啦啦啦啦";
        for (int i = 0; i < 10; i++){
            Random random = new Random();
            int j = random.nextInt(5);

            tagList.add("Tag" + s.substring(0, j) + i);
        }
        mViewBinding.tagLayout.setData(tagList);*/
//        mViewBinding.etMaterial.setUserFloatingLabel(true);

//        mViewBinding.textView.setText("jfowegjowgjoagjiowregpqowg");
//        mViewBinding.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initPermission();
//            }
//        });
//        ArrayList<Long> data = new ArrayList<>();
//        ArrayList<String> names = new ArrayList<>();
//        for (int i = 0; i< 100; i+= 15){
//            data.add(Long.valueOf(i+1));
//            names.add(i+"号");
//        }
//        mViewBinding.histogram.updateThisData(data, names);
//        initPermission();

//        SurfaceView
    }

    private void initWebView() {

        mViewBinding.webView.getSettings().setJavaScriptEnabled(true);
        mViewBinding.webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        mViewBinding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

        });
        mViewBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                // 处理外部链接打开新窗口，可以自定义新窗口的打开方式
                // 比如使用当前WebView来显示新内容
                // ...
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                // 处理JS对话框，比如alert, confirm, prompt
                // ...
                return true;
            }
        });
        mViewBinding.webView.loadUrl("file:///android_asset/test.html");

    }

    /*private void initPermission() {
        //先申请权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            goScanner();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE_CAMARE);
        }
    }

    private void goScanner() {
        Intent intent = new Intent(this, ScannerActivity.class);
        //这里可以用intent传递一些参数，比如扫码聚焦框尺寸大小，支持的扫码类型。
//        //设置扫码框的宽
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 400);
//        //设置扫码框的高
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 400);
//        //设置扫码框距顶部的位置
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 100);
//        //设置是否启用从相册获取二维码。
        intent.putExtra(Constant.EXTRA_IS_ENABLE_SCAN_FROM_PIC,true);
        Bundle bundle = new Bundle();
//        //设置支持的扫码类型
//        bundle.putSerializable(Constant.EXTRA_SCAN_CODE_TYPE, mHashMap);
        intent.putExtras(bundle);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISION_CODE_CAMARE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_REQUEST_CODE:
                    if (data == null) return;
                    String type = data.getStringExtra(Constant.EXTRA_RESULT_CODE_TYPE);
                    String content = data.getStringExtra(Constant.EXTRA_RESULT_CONTENT);
                    Toast.makeText(MainActivity.this, "codeType:" + type
                            + "-----content:" + content, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    public Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        return drawable;
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable)
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return bitmap;
    }
}