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
import android.widget.Toast;

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
        setContentView(R.layout.activity_main);
        mViewBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());
//        mViewBinding.textView.setText("jfowegjowgjoagjiowregpqowg");
//        mViewBinding.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initPermission();
//            }
//        });
        ArrayList<Long> data = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i< 100; i+= 15){
            data.add(Long.valueOf(i+1));
            names.add(i+"???");
        }
        mViewBinding.histogram.updateThisData(data, names);
//        initPermission();

//        SurfaceView
    }

    private void initPermission() {
        //???????????????
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            goScanner();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE_CAMARE);
        }
    }

    private void goScanner() {
        Intent intent = new Intent(this, ScannerActivity.class);
        //???????????????intent?????????????????????????????????????????????????????????????????????????????????
//        //?????????????????????
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 400);
//        //?????????????????????
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 400);
//        //?????????????????????????????????
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 100);
//        //?????????????????????????????????????????????
        intent.putExtra(Constant.EXTRA_IS_ENABLE_SCAN_FROM_PIC,true);
        Bundle bundle = new Bundle();
//        //???????????????????????????
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
    }
}