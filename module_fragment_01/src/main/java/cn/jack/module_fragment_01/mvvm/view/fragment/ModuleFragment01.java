package cn.jack.module_fragment_01.mvvm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_util.LogUtils;
import cn.jack.module_fragment_01.BR;
import cn.jack.module_fragment_01.R;
import cn.jack.module_fragment_01.databinding.FragmentHome01Binding;
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos;
import cn.jack.module_fragment_01.mvvm.view.adapter.HomeArticleInfoAdapter;
import cn.jack.module_fragment_01.mvvm.view.adapter.ImageNetAdapter;
import cn.jack.module_fragment_01.mvvm.vm.HomePageOneViewModle;
import jack.wrapper.base.mvvm.view.fragment.BaseFragment;

/**
 * 1.列表
 * 2.轮播图()
 * 3.搜索
 */
@Route(path = RouterPathFragment.HomeFirst.PAGER_HOME_FIRST)
public class ModuleFragment01 extends BaseFragment<FragmentHome01Binding, HomePageOneViewModle> implements OnRefreshLoadMoreListener {

    private boolean mIsRefresh;

    @Override
    public boolean isRegisterLoadSir() {
        return true;
    }

    @Override
    public boolean isViewRegisterLoadSir() {
        return true;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home_01;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public HomePageOneViewModle initViewModel() {
        return new HomePageOneViewModle(mApplication);
    }

    @Override
    public void prepareData() {
        super.prepareData();
        initAdapter();
        setLoadService(mBinding.refreshLayout);

        mIsRefresh = true;
        mViewModel.loadHomeInfos(true);
    }

    @Override
    protected void registorUIChangeLiveDataCallBack() {
        super.registorUIChangeLiveDataCallBack();

        mViewModel.mArticleInfos.observe(this, new Observer<List<ArticleInfo>>() {
            @Override
            public void onChanged(List<ArticleInfo> articleInfos) {
                if (mIsRefresh) {
                    mHomeArticleInfoAdapter.setList(articleInfos);
                } else {
                    mHomeArticleInfoAdapter.addData(articleInfos);
                }

                mBinding.refreshLayout.finishRefresh();
                mBinding.refreshLayout.finishLoadMore();
            }
        });

        mViewModel.mBannerInfos.observe(this, new Observer<List<BanInfos>>() {
            @Override
            public void onChanged(List<BanInfos> banInfos) {
                showBannerInfos(banInfos);
            }
        });
    }

    private void showBannerInfos(List<BanInfos> banInfos) {

        LogUtils.d("轮播图 ",banInfos);


        mBanner.setAdapter(new ImageNetAdapter(banInfos))
                .addBannerLifecycleObserver(this)                   //添加生命周期观察者
                .setIndicator(new CircleIndicator(getContext()))
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT);

    }

    private HomeArticleInfoAdapter mHomeArticleInfoAdapter;
    private void initAdapter() {
        mHomeArticleInfoAdapter = new HomeArticleInfoAdapter(R.layout.layout_home_article_item);
        mBinding.recyclerView.setAdapter(mHomeArticleInfoAdapter);
        addHeadView();
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.layout_head_view_of_banner_item, (ViewGroup) mBinding.recyclerView.getParent(), false);
        mHomeArticleInfoAdapter.addHeaderView(headView);
        initHeadview(headView);
    }

    private Banner mBanner;
    private void initHeadview(View headView) {
        mBanner = headView.findViewById(R.id.banner);
    }

    @Override
    public void dataReload() {
        mIsRefresh = true;
        mViewModel.loadHomeInfos(true);
    }

    @Override
    public void prepareListener() {
        super.prepareListener();
        mBinding.refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = false;
        mViewModel.loadHomeInfos(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        mViewModel.loadHomeInfos(true);
    }

}