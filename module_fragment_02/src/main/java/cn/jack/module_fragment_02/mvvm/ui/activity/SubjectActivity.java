package cn.jack.module_fragment_02.mvvm.ui.activity;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.OnTitleBarListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.List;
import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_common_business.adapter.ArticleInfoAdapter;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.module_fragment_02.BR;
import cn.jack.module_fragment_02.R;
import cn.jack.module_fragment_02.databinding.ActivityCollectionBinding;
import cn.jack.module_fragment_02.factory.ViewModelFactory;
import cn.jack.module_fragment_02.mvvm.SubjectViewModel;
import jack.wrapper.base.mvvm.view.activity.BaseActivity;

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 11:33
 * @描述 具体类型的列表
 */
@Route(path = RouterPathActivity.Subject.PAGER_SUBJECT)
public class SubjectActivity extends BaseActivity<ActivityCollectionBinding, SubjectViewModel> implements OnRefreshLoadMoreListener {

    @Autowired
    String articleTitle;

    @Autowired
    String articleId;

    private ArticleInfoAdapter mArticleInfoAdapter;
    private boolean mIsRefresh;

    @Override
    public int initContentView() {
        return R.layout.activity_collection;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public boolean injectARouter() {
        return true;
    }

    @Override
    public boolean isViewRegisterLoadSir() {
        return true;
    }

    @Override
    public boolean isRegisterLoadSir() {
        return true;
    }

    @Override
    public SubjectViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SubjectViewModel.class);
    }

    @Override
    protected void registorUIChangeLiveDataCallBack() {
        super.registorUIChangeLiveDataCallBack();
        mViewModel.mProjectInfoListUIChangeLiveData.observe(this, new Observer<ProjectInfoList>() {
            @Override
            public void onChanged(ProjectInfoList projectInfoList) {
                List<ArticleInfo> datas = projectInfoList.getDatas();
                for (ArticleInfo articleBean : datas) {
                    articleBean.setCollect(true);
                }

                if (mIsRefresh) {
                    mArticleInfoAdapter.setList(datas);
                } else {
                    mArticleInfoAdapter.addData(datas);
                }

                mBinding.subjectSmartRefreshLayout.finishRefresh();
                mBinding.subjectSmartRefreshLayout.finishLoadMore();
            }
        });

        mViewModel.mOnfailedUIChangeLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String str) {
                mBinding.subjectSmartRefreshLayout.finishRefresh();
                mBinding.subjectSmartRefreshLayout.finishLoadMore();
            }
        });
    }

    private void initAdapter() {
        mArticleInfoAdapter = new ArticleInfoAdapter();
        mBinding.subjectRecycleView.setAdapter(mArticleInfoAdapter);
    }

    @Override
    public void prepareData() {
        super.prepareData();
        mBinding.subjectTitleBar.setTitle(articleTitle);
        mBinding.subjectTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        mBinding.subjectSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        initAdapter();

        mIsRefresh = true;
        mViewModel.listSubject(true,articleId);

        //设置View展示状态布局
        setLoadService(mBinding.subjectSmartRefreshLayout);
    }

    @Override
    public void dataReload() {
        mIsRefresh = true;
        mViewModel.listSubject(true,articleId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        mViewModel.listSubject(true,articleId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        mViewModel.listSubject(true,articleId);
    }

}
