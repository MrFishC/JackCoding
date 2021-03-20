package cn.jack.module_fragment_02.ui.activity;

import androidx.annotation.NonNull;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.List;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.library_common_business.service.ApiArticleService;
import cn.jack.module_fragment_02.R;
import cn.jack.library_common_business.adapter.ArticleInfoAdapter;
import cn.jack.module_fragment_02.databinding.ActivityCollectionBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSubscriber;
import jack.retrofit2_rxjava2.manager.rx.RxFunction;
import jack.retrofit2_rxjava2.manager.rx.RxUtils;
import jack.wrapper.base.mvvm.view.activity.BaseSimpleActiviy;

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 11:33
 * @描述
 */
public class CollectionActivity extends BaseSimpleActiviy<ActivityCollectionBinding> implements OnRefreshLoadMoreListener {

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_collection;
    }

    private ArticleInfoAdapter mArticleInfoAdapter;

    private void initAdapter() {
        mArticleInfoAdapter = new ArticleInfoAdapter();
        mBinding.collectRecycleView.setAdapter(mArticleInfoAdapter);
    }

    @Override
    public void prepareData() {
        super.prepareData();

        mBinding.collectSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        initAdapter();

        listMyCollect(true);
    }

    private int page = 0;

    private RxBaseSubscriber<ProjectInfoList> mCollectionArticleInfos;

    private void listMyCollect(boolean refresh) {
        if (refresh) {
            page = 0;
        } else {
            page++;
        }

        mCollectionArticleInfos = new RxBaseSubscriber<ProjectInfoList>() {

            @Override
            public void onFailed(ApiException e) {
                mBinding.collectSmartRefreshLayout.finishRefresh();
                mBinding.collectSmartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onSuccess(ProjectInfoList data) {

                List<ArticleInfo> datas = data.getDatas();
                for (ArticleInfo articleBean : datas) {
                    articleBean.setCollect(true);
                }

                if (refresh) {
                    mArticleInfoAdapter.setList(datas);
                } else {
                    mArticleInfoAdapter.addData(datas);
                }

                mBinding.collectSmartRefreshLayout.finishRefresh();
                mBinding.collectSmartRefreshLayout.finishLoadMore();
            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiArticleService.class)
                .getArticleCollection(page)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<ProjectInfoList>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mCollectionArticleInfos);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        listMyCollect(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        listMyCollect(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.getInstance().dispose(mCollectionArticleInfos);
    }

}
