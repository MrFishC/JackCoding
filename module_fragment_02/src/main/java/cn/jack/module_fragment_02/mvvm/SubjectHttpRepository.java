package cn.jack.module_fragment_02.mvvm;

import javax.inject.Inject;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.library_common_business.service.api.ApiArticleService;
import cn.jack.module_fragment_02.contract.ISubjectLisenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ErrorStatusInfo;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSubscriber;
import jack.retrofit2_rxjava2.manager.rx.RxFunction;
import jack.retrofit2_rxjava2.manager.rx.RxUtils;
import jack.wrapper.base.mvvm.model.BaseModel;

/**
 * @创建者 Jack
 * @创建时间 2021/3/30 16:56
 * @描述
 */
public class SubjectHttpRepository extends BaseModel {

    @Inject
    public SubjectHttpRepository() {
    }

    private int mPage;

    public void pageSubject(boolean refresh,String articleId, ISubjectLisenter iSubjectLisenter) {
        if (refresh) {
            mPage = 0;
        } else {
            mPage++;
        }

        RxUtils.getInstance()
                .obtainRetrofitService(ApiArticleService.class)
                .pageArticleList(mPage,articleId)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<ProjectInfoList>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxBaseSubscriber<ProjectInfoList>(this) {

                    @Override
                    public void onFailed(ErrorStatusInfo errorStatusInfo) {
                        iSubjectLisenter.loadFailed(errorStatusInfo);
//                        iSubjectLisenter.showToast(e.getErrorMessage());
                    }

                    @Override
                    public void onSuccess(ProjectInfoList data) {
                        if(data != null){
                            if (data.getDatas().size() == 0) {
                                iSubjectLisenter.loadEmpty();
                            }else {
                                iSubjectLisenter.onSuccess(data);
                                iSubjectLisenter.loadSuccess();
                            }
                        }
                    }

                });
    }
}
