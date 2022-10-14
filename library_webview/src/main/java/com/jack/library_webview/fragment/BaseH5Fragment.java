package com.jack.library_webview.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jack.library_webview.R;
import com.jack.library_webview.cache.WebViewCacheU;
import com.jack.library_webview.util.WebConstants;
import com.jack.library_webview.base.callback.WebViewCallBack;
import com.jack.library_webview.base.set.WebViewSettingManager;
import com.jack.library_webview.base.webview.BaseWebView;
import com.jack.library_webview.databinding.LayoutWebviewContainerBinding;
import com.jack.library_webview.dispatcher.CommandDispatcher;
import com.jack.library_webview.request.ExecuteLisenter;
import com.jack.library_webview.util.LogW;

import java.util.Objects;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 22:30
 * @描述
 */
public class BaseH5Fragment extends Fragment implements WebViewCallBack, ExecuteLisenter {

    protected LayoutWebviewContainerBinding mBinding;
    private   BaseWebView                   mWebView;
    private   String                        mWebUrl;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CommandDispatcher.getInstance().initAidlConnect(context.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_webview_container, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //        CommandDispatcher.getInstance().initAidlConnect(Objects.requireNonNull(getContext()).getApplicationContext());
        initLayout();
        addWebView();
    }

    private View mLoadingView;
    private View mErrorView;

    private void initLayout() {
        LayoutInflater inflater = getLayoutInflater();
        mLoadingView = inflater.inflate(R.layout.layout_loading, null);
        mErrorView = inflater.inflate(R.layout.layout_error, null);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
    }

    private void reload() {
        if (mBinding.rootView.getChildCount() > 0) {
            mBinding.rootView.removeAllViews();
            mBinding.rootView.addView(mWebView);
            mWebView.reload();
        }
    }

    @SuppressLint("AddJavascriptInterface")
    private void addWebView() {
        //mWebView = new BaseWebView(getContext());
        mWebView = WebViewCacheU.Companion.acquireWebViewInternal(Objects.requireNonNull(getContext()));
        WebViewSettingManager.getInstance().settings(mWebView);

        if (mWebView.getParent() != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        }

        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        mBinding.rootView.addView(mWebView);

        mWebView.setExecuteLisenter(this);

        mWebView.initWebview(getContext(), this);
        loadUrl();
    }

    private void loadUrl() {
        mWebView.loadUrl(mWebUrl);
        //测试加载本地HTML
//        mWebView.loadUrl("file:///android_asset/aidl.html");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mLoadingView = null;
        mErrorView = null;
    }

    @Override
    public void onPageStarted(String url) {
        System.out.println("onPageStarted");

        if (mBinding.rootView.getChildCount() > 0) {
            mBinding.rootView.removeAllViews();
            mBinding.rootView.addView(mLoadingView);
        }
    }

    @Override
    public void onPageFinished(String url) {
        System.out.println("onPageFinished");
        if (mBinding.rootView.getChildCount() > 0) {
            mBinding.rootView.removeAllViews();
            mBinding.rootView.addView(mWebView);
        }
    }

    @Override
    public void onReceivedError() {
        System.out.println("onReceivedError");

        if (mBinding.rootView.getChildCount() > 0) {
            mBinding.rootView.removeAllViews();
            mBinding.rootView.addView(mErrorView);
            mWebView.stopLoading();
        }
    }

    @Override
    public void onReceivedHttpError() {
        System.out.println("onReceivedHttpError");
        if (mBinding.rootView.getChildCount() > 0) {
            mBinding.rootView.removeAllViews();
            mBinding.rootView.addView(mErrorView);
            mWebView.stopLoading();
        }
    }

    @Override
    public int getCommandLevel(int levelCommand) {
        return levelCommand;
    }

    @Override
    public void executeRequest(Context context, int commandLevel, String cmd, String param, WebView webView) {
        LogW.Companion.d(" executeRequest " + " commandLevel " + commandLevel + " cmd " + cmd + " param " + param);
        CommandDispatcher.getInstance().execute(context, commandLevel, cmd, param, mWebView);
    }

    @Override
    public void handleCallback(String result) {
        //H5页面--->原生页面--->H5页面自身
        //远程调用后，结果又返回到这里了
        LogW.Companion.d(" handleCallback  " + " result " + result);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return onBackHandle();
        }
        return false;
    }

    private boolean onBackHandle() {
        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
