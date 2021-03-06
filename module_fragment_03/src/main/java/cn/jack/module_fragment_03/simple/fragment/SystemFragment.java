package cn.jack.module_fragment_03.simple.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import cn.jack.library_arouter.manager.ArouterManager;
import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.library_common_business.loadsir.ViewStateLayout;
import cn.jack.module_fragment_03.R;
import cn.jack.module_fragment_03.databinding.FragmentSystemBinding;
import cn.jack.module_fragment_03.entity.SystemAndSquareInfo;
import cn.jack.module_fragment_03.service.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSimpleSubscriber;
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

    @Override
    public boolean isRegisterLoadSir() {
        return true;
    }

    private RxBaseSimpleSubscriber<List<SystemAndSquareInfo>> mRxBaseSimpleSubscriber;

    @Override
    public void prepareData() {
        super.prepareData();

        httpDataInfo();
    }

    @Override
    public void dataReload() {
        httpDataInfo();
    }

    private void httpDataInfo() {
        mRxBaseSimpleSubscriber = new RxBaseSimpleSubscriber<List<SystemAndSquareInfo>>() {

            @Override
            public void onFailed(ApiException e) {
                setViewStateChangeLisenter(ViewStateLayout.FAILED);
            }

            @Override
            public void onSuccess(List<SystemAndSquareInfo> treeListRes) {
                setData(treeListRes);
                if(treeListRes.size() == 0){
                    setViewStateChangeLisenter(ViewStateLayout.EMPTY);
                }else {
                    setViewStateChangeLisenter(ViewStateLayout.SUCCESS);
                }
            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .getSquareInfoList()
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<List<SystemAndSquareInfo>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRxBaseSimpleSubscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.getInstance().dispose(mRxBaseSimpleSubscriber);
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
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArouterManager.getInstance().navigation2Subject(child.getName(),child.getId());
                    }
                });
                flexboxLayout.addView(textView);
            }
            mBinding.linearContainer.addView(view);
        }
    }

}
