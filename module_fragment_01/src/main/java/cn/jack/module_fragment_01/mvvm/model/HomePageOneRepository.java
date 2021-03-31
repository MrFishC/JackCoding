package cn.jack.module_fragment_01.mvvm.model;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.module_fragment_01.api.ApiService;
import cn.jack.module_fragment_01.contract.IPageOneLisenter;
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos;
import cn.jack.module_fragment_01.mvvm.model.entity.HomeInfos;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSubscriber;
import jack.retrofit2_rxjava2.manager.rx.RxFunction;
import jack.retrofit2_rxjava2.manager.rx.RxUtils;
import jack.wrapper.base.mvvm.model.BaseModel;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 15:59
 * @描述
 */
public class HomePageOneRepository extends BaseModel {

    private int mPage;
    public void homeInfos(boolean refresh, IPageOneLisenter iPageOneLisenter) {
        if (refresh) {
            mPage = 0;
        } else {
            mPage++;
        }

        //使用合并操作符
        Observable.zip(RxUtils
                        .getInstance()
                        .obtainRetrofitService(ApiService.class)
                        .bannerInfos()
                        .subscribeOn(Schedulers.io())
                        .map(new RxFunction<List<BanInfos>>()),
                RxUtils
                        .getInstance()
                        .obtainRetrofitService(ApiService.class)
                        .homeArticleList(mPage)
                        .subscribeOn(Schedulers.io())
                        .map(new RxFunction<ProjectInfoList>()),
                new BiFunction<List<BanInfos>, ProjectInfoList, HomeInfos>() {
                    @Override
                    public @NotNull HomeInfos apply(@NotNull List<BanInfos> bannerInfoList, @NotNull ProjectInfoList projectInfoList) throws Exception {
                        return new HomeInfos(bannerInfoList,projectInfoList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxBaseSubscriber<HomeInfos>(this) {
                    @Override
                    public void onFailed(ApiException e) {
                        iPageOneLisenter.loadFailed();
                    }

                    @Override
                    public void onSuccess(HomeInfos homeInfos) {

                        List<BanInfos> banInfos = homeInfos.getBannerInfo();
                        ProjectInfoList projectInfoList = homeInfos.getProjectInfoList();
                        List<ArticleInfo> articleInfos = projectInfoList.getDatas();

                        iPageOneLisenter.showBannerInfos(banInfos);

                        if(articleInfos.size() == 0 && mPage == 0){
                            iPageOneLisenter.loadEmpty();
                        }else {
                            iPageOneLisenter.showPageInfos(projectInfoList.getDatas());
                            iPageOneLisenter.loadSuccess();
                        }

                    }
                });

    }
}
