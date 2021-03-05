package jack.wrapper.base.mvvm.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import jack.wrapper.base.mvvm.view.IBaseView;

/**
 * @创建者 Jack
 * @创建时间 2021/3/4 18:20
 * @描述
 */
abstract class BaseTopFragment extends Fragment implements IBaseView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //todo 沉寂式状态栏的封装

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