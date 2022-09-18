package cn.jack.module_fragment_02.mvvm

import androidx.lifecycle.viewModelScope
import cn.jack.library_common_business.entiy.ProjectInfoList
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import com.jack.lib_wrapper_net.model.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/30 16:56
 * @描述
 */
@HiltViewModel
class SubjectViewModel @Inject constructor(private val mRepository: SubjectHttpRepository) :
    BaseWrapperViewModel() {

    private val mProjectInfoList =
        MutableStateFlow<EventResult<ProjectInfoList>>(EventResult.OnComplete)

    val projectInfoList_ = mProjectInfoList.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    fun listSubject(refresh: Boolean, articleId: String) {
        mRepository.pageSubject(refresh, articleId).onEach {
            mProjectInfoList.value = it
        }.launchIn(viewModelScope)
    }

}