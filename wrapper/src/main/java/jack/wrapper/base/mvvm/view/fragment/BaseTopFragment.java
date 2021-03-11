package jack.wrapper.base.mvvm.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import jack.wrapper.R;
import jack.wrapper.base.mvvm.view.IBaseView;

/**
 * @创建者 Jack
 * @创建时间 2021/3/4 18:20
 * @描述
 */
abstract class BaseTopFragment extends Fragment implements IBaseView {

    /**
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        if(isBlack()){
            ImmersionBar
                    .with(this)
                    .statusBarView(R.id.status_bar_view)
                    .statusBarDarkFont(true)
//                    .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                    .init();
        }else {
            ImmersionBar
                    .with(this)
                    .statusBarView(R.id.status_bar_view)
                    .init();
        }
    }

    /**
     * 状态栏默认为黑色
     * @return
     */
    protected boolean isBlack() {
        return true;
    }

    //空实现部分方法,方便子类选择性实现
    //初始化参数
    @Override
    public void prepareParam() {

    }

    //初始化数据
    @Override
    public void prepareData() {

    }

    //设置监听
    @Override
    public void prepareListener() {

    }

}