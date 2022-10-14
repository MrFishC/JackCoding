package cn.jack.module_fragment_01.mvvm.vm

import androidx.lifecycle.viewModelScope
import cn.jack.module_fragment_01.mvvm.model.entity.HomeInfos
import cn.jack.module_fragment_01.mvvm.model.repository.HomePageOneRepository
import com.jack.lib_wrapper_mvvm.mvvm.viewmodel.BaseWrapperViewModel
import com.jack.lib_wrapper_net.model.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 16:00
 * @描述
 */
@HiltViewModel
class HomePageOneViewModle @Inject constructor(private val mRepository: HomePageOneRepository) :
    BaseWrapperViewModel() {

    private val mHomeInfos = MutableStateFlow<EventResult<HomeInfos>>(EventResult.OnComplete)

    val homeInfos_ = mHomeInfos.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    fun loadHomeInfos(refresh: Boolean) {
        mRepository.homeInfos(refresh).onEach {
            mHomeInfos.value = it
        }.launchIn(viewModelScope)
    }
}