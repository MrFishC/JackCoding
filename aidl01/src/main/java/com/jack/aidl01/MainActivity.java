package com.jack.aidl01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 在使用最基础的aidl时遇到的问题
 *
 * 1.在aidl02中 bindService后，点击sendNums方法，提示未连接
 *      01.intent.setPackage和setAction未设置；
 *      02.GetTwoNumbersService的<intent-filter>未设置
 *
 *
 *
 * 服务的启动方式
 * activity跟service之间的通信
 * aidl的简单使用
 * aidl的原理
 * 多个aidl的使用，如何封装
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}