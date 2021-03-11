package cn.jack.module_fragment_02;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_02.adapter.ArticleInfoAdapter;
import cn.jack.module_fragment_02.databinding.FragmentHome02Binding;
import cn.jack.module_fragment_02.entiy.ProjectInfoList;
import cn.jack.module_fragment_02.service.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSubscriber;
import jack.retrofit2_rxjava2.manager.rx.RxFunction;
import jack.retrofit2_rxjava2.manager.rx.RxUtils;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

@Route(path = RouterPathFragment.HomeSecond.PAGER_HOME_SECOND)
public class ModuleFragment02 extends BaseSimpleFragment<FragmentHome02Binding> implements OnRefreshLoadMoreListener {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_home_02;
    }

    @Override
    public void prepareData() {
        super.prepareData();

        initAdapter();

        listProjects(id, true);
    }

    @Override
    public void prepareListener() {
        super.prepareListener();

        mBinding.findSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    private ArticleInfoAdapter mArticleInfoAdapter;

    private void initAdapter() {
        mArticleInfoAdapter = new ArticleInfoAdapter();
        mBinding.findRecycleView.setAdapter(mArticleInfoAdapter);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        listProjects(id, false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        listProjects(id, true);
    }

    private int page = 0;
    private String id;

    private RxBaseSubscriber<ProjectInfoList> mProjectInfoListRxBaseSubscriber;
    private void listProjects(String id, boolean refresh) {

        if (refresh) {
            page = 0;
        } else {
            page++;
        }

        mProjectInfoListRxBaseSubscriber = new RxBaseSubscriber<ProjectInfoList> () {

            @Override
            public void onError(ApiException e) {
                mBinding.findSmartRefreshLayout.finishRefresh();
                mBinding.findSmartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onSuccess(ProjectInfoList data) {
                if (refresh) {
                    mArticleInfoAdapter.setList(data.getDatas());
                } else {
                    mArticleInfoAdapter.addData(data.getDatas());
                }

                mBinding.findSmartRefreshLayout.finishRefresh();
                mBinding.findSmartRefreshLayout.finishLoadMore();
            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .listProjects(page,id)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<ProjectInfoList>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mProjectInfoListRxBaseSubscriber);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.getInstance().dispose(mProjectInfoListRxBaseSubscriber);
    }

}