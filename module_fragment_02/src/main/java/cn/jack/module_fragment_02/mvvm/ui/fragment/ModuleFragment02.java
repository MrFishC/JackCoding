package cn.jack.module_fragment_02.mvvm.ui.fragment;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.hjq.bar.OnTitleBarListener;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mmkv.MMKV;

import java.util.List;

import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.library_common_business.adapter.ArticleInfoAdapter;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.library_common_business.loadsir.ViewStateLayout;
import cn.jack.library_common_business.service.api.ApiArticleService;
import cn.jack.library_util.AppContext;
import cn.jack.module_fragment_02.R;
import cn.jack.module_fragment_02.databinding.FragmentHome02Binding;
import cn.jack.module_fragment_02.entiy.ProjectSortInfo;
import cn.jack.module_fragment_02.service.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSimpleSubscriber;
import jack.retrofit2_rxjava2.manager.rx.RxFunction;
import jack.retrofit2_rxjava2.manager.rx.RxUtils;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

/**
 * 1.项目列表
 * 2.项目分类（条件筛选）
 */
@Route(path = RouterPathFragment.HomeSecond.PAGER_HOME_SECOND)
public class ModuleFragment02 extends BaseSimpleFragment<FragmentHome02Binding> implements OnRefreshLoadMoreListener, OnItemChildClickListener {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_home_02;
    }

    @Override
    public boolean isRegisterLoadSir() {
        return true;
    }

    @Override
    public boolean isViewRegisterLoadSir() {
        return true;
    }

    @Override
    public void prepareData() {
        super.prepareData();

        initAdapter();

        //在fragment中的view中使用loadsir
        setLoadService(mBinding.findSmartRefreshLayout);
    }

    @Override
    protected void loadData() {
        super.loadData();

        listProjects(id, true);
    }

    @Override
    public void dataReload() {
        listProjects(id, true);
    }

    @Override
    public void prepareListener() {
        super.prepareListener();
        mBinding.findSmartRefreshLayout.setOnRefreshLoadMoreListener(this);

        mBinding.projectSortTitleBar.getLeftView().setVisibility(View.GONE);
        mBinding.projectSortTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                showSortDailog();
            }
        });
    }

    private void showSortDailog() {
        //查询本地数据
        MMKV kv = MMKV.defaultMMKV();
        if(kv != null){
            String sortJson = kv.decodeString("sort","");
            if(!TextUtils.isEmpty(sortJson)){

                System.out.println(" !isEmpty ");

                List<ProjectSortInfo> sortInfos = JSON.parseArray(sortJson, ProjectSortInfo.class);
                showSortDialog(sortInfos,true, true, true,
                        "项目分类", true, false);
            }else {
                //无数据,请求接口
                System.out.println(" isEmpty ");
                getSortInfos();
            }
        }

    }

    private RxBaseSimpleSubscriber<List<ProjectSortInfo>> mSortInfosRxBaseSimpleSubscriber;
    private void getSortInfos() {
        mSortInfosRxBaseSimpleSubscriber = new RxBaseSimpleSubscriber<List<ProjectSortInfo>> () {

            @Override
            public void onFailed(ApiException e) {
                System.out.println(e.getErrorMessage());
            }

            @Override
            public void onSuccess(List<ProjectSortInfo> projectSortInfos) {
                showSortDialog(projectSortInfos,true, true, true,
                        "项目分类", true, false);
                //将数据存储在本地
                keepSortInfosInLocal(projectSortInfos);
            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .projectSortInfos()
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<List<ProjectSortInfo>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSortInfosRxBaseSimpleSubscriber);
    }

    private void keepSortInfosInLocal(List<ProjectSortInfo> projectSortInfos) {
        String sortJson = JSON.toJSONString(projectSortInfos);
        MMKV kv = MMKV.defaultMMKV();
        if(kv != null){
            kv.encode("sort",sortJson);
        }
    }

    private void showSortDialog(List<ProjectSortInfo> sortInfos, boolean gravityCenter,
                                boolean addCancelBtn,
                                boolean withIcon,
                                CharSequence title,
                                boolean allowDragDismiss,
                                boolean withMark) {

        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(getActivity());
            builder.setGravityCenter(gravityCenter)
                    .setSkinManager(QMUISkinManager.defaultInstance(AppContext.getContext()))
                    .setTitle(title)
                    .setAddCancelBtn(addCancelBtn)
                    .setAllowDrag(allowDragDismiss)
                    .setNeedRightMark(withMark)
                    .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                        @Override
                        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                            dialog.dismiss();
                            mBinding.projectSortTitleBar.setTitle(sortInfos.get(position).getName());
                            listProjects(tag,true);
                        }
                    });
            if (withMark) {
                builder.setCheckedIndex(40);
            }

            for (int i = 0; i < sortInfos.size(); i++) {
                ProjectSortInfo projectSortInfo = sortInfos.get(i);
                if(!TextUtils.isEmpty(projectSortInfo.getName()) && !TextUtils.isEmpty(projectSortInfo.getCourseId())) {
                    builder.addItem(projectSortInfo.getName(), projectSortInfo.getCourseId());
                }
            }

            builder.build().show();
    }

    private ArticleInfoAdapter mArticleInfoAdapter;

    private void initAdapter() {
        mArticleInfoAdapter = new ArticleInfoAdapter();
        mBinding.findRecycleView.setAdapter(mArticleInfoAdapter);
        mArticleInfoAdapter.setOnItemChildClickListener(this);
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

    private RxBaseSimpleSubscriber<ProjectInfoList> mProjectInfoListRxBaseSimpleSubscriber;

    private void listProjects(String id, boolean refresh) {

        if (refresh) {
            page = 0;
        } else {
            page++;
        }

        mProjectInfoListRxBaseSimpleSubscriber = new RxBaseSimpleSubscriber<ProjectInfoList> () {

            @Override
            public void onFailed(ApiException e) {
                mBinding.findSmartRefreshLayout.finishRefresh();
                mBinding.findSmartRefreshLayout.finishLoadMore();
                setViewStateChangeLisenter(ViewStateLayout.FAILED);
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

                if(data.getDatas().size() == 0){
                    setViewStateChangeLisenter(ViewStateLayout.EMPTY);
                }else {
                    setViewStateChangeLisenter(ViewStateLayout.SUCCESS);
                }

            }

//            @Override
//            public void onTimeOut() {
//                super.onTimeOut();
//                setViewStateChangeLisenter(ViewStateLayout.TIME_OUT);
//            }
//
//            @Override
//            public void onNetError() {
//                super.onNetError();
//                setViewStateChangeLisenter(ViewStateLayout.NET_ERROR);
//            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .listProjects(page,id)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<ProjectInfoList>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mProjectInfoListRxBaseSimpleSubscriber);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.getInstance().dispose(mProjectInfoListRxBaseSimpleSubscriber);
        RxUtils.getInstance().dispose(mSortInfosRxBaseSimpleSubscriber);
        RxUtils.getInstance().dispose(mUnCollectArticleRxbaseSubacriber);
        RxUtils.getInstance().dispose(mCollectArticleRxbaseSubacriber);
    }

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        ArticleInfo articleInfo = (ArticleInfo) adapter.getItem(position);
        if(articleInfo != null){
            if(articleInfo.isCollect()){
                uncollectArticle(articleInfo,position,articleInfo.getId());
            }else {
                collectArticle(articleInfo,position,articleInfo.getId());
            }
        }
    }

    private RxBaseSimpleSubscriber<String> mCollectArticleRxbaseSubacriber;
    private void collectArticle(ArticleInfo articleInfo,int position,String id) {
        mCollectArticleRxbaseSubacriber = new RxBaseSimpleSubscriber<String>() {

            @Override
            public void onFailed(ApiException e) {

            }

            @Override
            public void onSuccess(String str) {
                articleInfo.setCollect(!articleInfo.isCollect());
                mArticleInfoAdapter.notifyItemChanged(position);
            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiArticleService.class)
                .collectAtrticle(id)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<String>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mCollectArticleRxbaseSubacriber);
    }

    private RxBaseSimpleSubscriber<String> mUnCollectArticleRxbaseSubacriber;
    private void uncollectArticle(ArticleInfo articleInfo,int position,String id) {
        mUnCollectArticleRxbaseSubacriber = new RxBaseSimpleSubscriber<String>() {

            @Override
            public void onFailed(ApiException e) {

            }

            @Override
            public void onSuccess(String str) {
                articleInfo.setCollect(!articleInfo.isCollect());
                mArticleInfoAdapter.notifyItemChanged(position);
            }

        };

        RxUtils.getInstance()
                .obtainRetrofitService(ApiArticleService.class)
                .unCollectAtrticle(id)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<String>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUnCollectArticleRxbaseSubacriber);
    }

}