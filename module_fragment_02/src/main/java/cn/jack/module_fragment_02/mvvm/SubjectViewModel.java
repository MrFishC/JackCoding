package cn.jack.module_fragment_02.mvvm;

import android.app.Application;
import androidx.annotation.NonNull;
import javax.inject.Inject;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.library_util.LogUtils;
import cn.jack.module_fragment_02.contract.ISubjectLisenter;
//import cn.jack.module_fragment_02.di.DaggerViewModelComponent;
import jack.retrofit2_rxjava2.exception.ErrorStatusInfo;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;
import jack.wrapper.base.mvvm.viewModel.liveData.UIChangeLiveData;

/**
 * @创建者 Jack
 * @创建时间 2021/3/30 16:56
 * @描述
 */
public class SubjectViewModel extends BaseViewModel<SubjectHttpRepository> implements ISubjectLisenter {

    @Inject
    SubjectHttpRepository mSubjectHttpRepository;

    public UIChangeLiveData<ProjectInfoList> mProjectInfoListUIChangeLiveData = new UIChangeLiveData<>();
    public UIChangeLiveData<String> mOnfailedUIChangeLiveData = new UIChangeLiveData<>();

    public SubjectViewModel(@NonNull Application application) {
        super(application);

        LogUtils.d("初始化实际 " + System.currentTimeMillis());
//        DaggerViewModelComponent.builder().build().inject(this);
        mModel = mSubjectHttpRepository;
    }

    public void listSubject(boolean refresh,String articleId) {
        mModel.pageSubject(refresh,articleId,this);
    }

    @Override
    public void onSuccess(ProjectInfoList projectInfoList) {
        mProjectInfoListUIChangeLiveData.setValue(projectInfoList);
    }

    @Override
    public void loadFailed(ErrorStatusInfo errorStatusInfo) {
        super.loadFailed(errorStatusInfo);
        mOnfailedUIChangeLiveData.setValue("");
    }

}
