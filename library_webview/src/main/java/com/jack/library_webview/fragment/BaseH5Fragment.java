package com.jack.library_webview.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jack.library_command.command.constants.CommandConstants;
import com.jack.library_webview.R;
import com.jack.library_webview.WebConstants;
import com.jack.library_webview.base.callback.WebViewCallBack;
import com.jack.library_webview.base.set.WebViewSettingManager;
import com.jack.library_webview.base.webview.BaseWebView;
import com.jack.library_webview.databinding.LayoutWebviewContainerBinding;
import com.jack.library_webview.dispatcher.CommandDispatcher;
import com.jack.library_webview.request.ExecuteLisenter;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 22:30
 * @描述
 */
public class BaseH5Fragment extends Fragment implements WebViewCallBack, ExecuteLisenter {

    protected LayoutWebviewContainerBinding mBinding;
    private BaseWebView mWebView;
    private String mWebUrl;

    public static BaseH5Fragment newInstance(String url) {
        BaseH5Fragment fragment = new BaseH5Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(WebConstants.C_CONFIG.URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mWebUrl = bundle.getString(WebConstants.C_CONFIG.URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_webview_container, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommandDispatcher.getInstance().initAidlConnect(getContext());
        addWebView();
    }

    @SuppressLint("AddJavascriptInterface")
    private void addWebView() {
        mWebView = new BaseWebView(getContext());
        WebViewSettingManager.getInstance().settings(mWebView);

        if (mWebView.getParent() != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        }

        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        mBinding.rootView.addView(mWebView);

        mWebView.setExecuteLisenter(this);

        mWebView.initWebview(getContext(),this);
        loadUrl();
    }

    private void loadUrl(){
        mWebView.loadUrl(mWebUrl);
        //测试加载本地HTML
//        mWebView.loadUrl("file:///android_asset/aidl.html");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //todo

    }

    @Override
    public void onPageStarted(String url) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onReceivedError() {

    }

    @Override
    public void onReceivedHttpError() {

    }

    public int getCommandLevel(){
        return CommandConstants.LEVEL_LOCAL;        //temp 写固定参数不好拓展。
    }

    @Override
    public void executeRequest(Context context, int commandLevel, String cmd, WebView webView) {
        CommandDispatcher.getInstance().execute(context,commandLevel,cmd,mWebView);
    }

    @Override
    public void handleCallback(String result) {
        //远程调用后，结果又返回到这里了
        System.out.println(" 调用结束了 ");
    }

}
