package cn.jack.module_fragment_01.mvvm.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import javax.inject.Inject;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.module_fragment_01.contract.IPageOneLisenter;
//import cn.jack.module_fragment_01.di.DaggerViewModelComponent;
import cn.jack.module_fragment_01.mvvm.model.Repository.HomePageOneRepository;
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 16:00
 * @描述
 */
public class HomePageOneViewModle extends BaseViewModel<HomePageOneRepository> implements IPageOneLisenter {

    @Inject
    HomePageOneRepository mHomePageOneRepository;

    public MutableLiveData<List<BanInfos>> mBannerInfos = new MutableLiveData<>();
    public MutableLiveData<List<ArticleInfo>> mArticleInfos = new MutableLiveData<>();

    public HomePageOneViewModle(@NonNull Application application) {
        super(application);
//        DaggerViewModelComponent.builder().build().inject(this);
        mModel = mHomePageOneRepository;
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
