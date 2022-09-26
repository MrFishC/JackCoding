package com.jack.library_webview;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jack.library_webview.fragment.BaseH5Fragment;

import cn.jack.library_arouter.BundleParams;
import cn.jack.library_arouter.router.RouterPathActivity;

/**
 * @创建者 Jack
 * @创建时间 2022/9/20 1:59
 * @描述
 */
@Route(path = RouterPathActivity.Web.PAGER_WEB)
public class WebActivity extends AppCompatActivity {

    BaseH5Fragment mBaseH5Fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);

        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.web_view_fragment, BaseH5Fragment.newInstance(getIntent().getExtras().getString(BundleParams.WEB_URL))).commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mBaseH5Fragment != null) {
            return mBaseH5Fragment.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
