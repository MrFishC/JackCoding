package cn.jack.module_fragment_01.mvvm.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.module_fragment_01.contract.IPageOneLisenter;
import cn.jack.module_fragment_01.mvvm.model.HomePageOneRepository;
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;
import jack.wrapper.base.mvvm.viewModel.liveData.UIChangeLiveData;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 16:00
 * @描述
 */
public class HomePageOneViewModle extends BaseViewModel<HomePageOneRepository> implements IPageOneLisenter {

    public MutableLiveData<List<BanInfos>> mBannerInfos = new MutableLiveData<>();
    public MutableLiveData<List<ArticleInfo>> mArticleInfos = new MutableLiveData<>();

    public HomePageOneViewModle(@NonNull Application application, HomePageOneRepository model) {
        super(application, model);
    }

    public void loadHomeInfos(boolean refresh){
        mModel.homeInfos(refresh,this);
    }

    @Override
    public void showBannerInfos(List<BanInfos> banInfosList) {
        mBannerInfos.setValue(banInfosList);
    }

    @Override
    public void showPageInfos(List<ArticleInfo> articleInfos) {
        mArticleInfos.setValue(articleInfos);
    }

}
