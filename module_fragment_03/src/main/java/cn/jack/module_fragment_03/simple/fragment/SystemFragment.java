package cn.jack.module_fragment_03.simple.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexboxLayout;
import com.skydoves.indicatorscrollview.IndicatorAnimation;
import com.skydoves.indicatorscrollview.IndicatorItem;

import java.util.List;

import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_03.R;
import cn.jack.module_fragment_03.databinding.FragmentSystemBinding;
import cn.jack.module_fragment_03.entity.ArticleInfo;
import cn.jack.module_fragment_03.entity.SystemAndSquareInfo;
import cn.jack.module_fragment_03.service.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSubscriber;
import jack.retrofit2_rxjava2.manager.rx.RxFunction;
import jack.retrofit2_rxjava2.manager.rx.RxUtils;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

/**
 * Description: 体系
 *
 * 使用随机布局实现
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_SYSTEM)
public class SystemFragment extends BaseSimpleFragment<FragmentSystemBinding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_system;
    }

    private RxBaseSubscriber<List<SystemAndSquareInfo>> mRxBaseSubscriber;

    @Override
    public void prepareData() {
        super.prepareData();

        mRxBaseSubscriber = new RxBaseSubscriber<List<SystemAndSquareInfo>>() {

            @Override
            public void onFailed(ApiException e) {

            }

            @Override
            public void onSuccess(List<SystemAndSquareInfo> treeListRes) {
                setData(treeListRes);
            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .getSquareInfoList()
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<List<SystemAndSquareInfo>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRxBaseSubscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.getInstance().dispose(mRxBaseSubscriber);
    }

    private LayoutInflater layoutInflater = null;

    private View findItem() {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(getContext());
        return (View) layoutInflater.inflate(R.layout.layout_square_item, null, false);
    }

    private AppCompatTextView findLabel(FlexboxLayout flexboxLayout) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(getContext());
        return (AppCompatTextView) layoutInflater.inflate(R.layout.layout_flexbox_text_item, flexboxLayout, false);
    }

    protected void setData(List<SystemAndSquareInfo> data) {
        mBinding.linearContainer.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            SystemAndSquareInfo res = data.get(i);
            String name = res.getName();
            View view = findItem();
            TextView tvTitle = view.findViewById(R.id.text_name);
            tvTitle.setText(name);
            FlexboxLayout flexboxLayout = view.findViewById(R.id.flex_layout);

            List<SystemAndSquareInfo.ChildrenBean> children = res.getChildren();
            for (SystemAndSquareInfo.ChildrenBean child : children) {
                AppCompatTextView textView = findLabel(flexboxLayout);
                textView.setText(child.getName());
                flexboxLayout.addView(textView);
            }
            mBinding.linearContainer.addView(view);
        }
    }

}
