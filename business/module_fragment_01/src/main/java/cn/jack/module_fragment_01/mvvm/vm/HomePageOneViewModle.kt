package cn.jack.module_fragment_01.mvvm.vm

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos
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

    private val mBinnerInfos = MutableStateFlow<EventResult<List<BanInfos>>>(EventResult.OnComplete)
    val binnerInfos_ = mBinnerInfos.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    private val mHomeInfos =
        MutableStateFlow<EventResult<List<ArticleInfo>>>(EventResult.OnComplete)

    val homeInfos_ = mHomeInfos.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    private val collectAtrticle =
        MutableStateFlow<EventResult<String>>(EventResult.OnComplete)

    val collectAtrticle_ =
        collectAtrticle.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    private val uncollectArticle =
        MutableStateFlow<EventResult<String>>(EventResult.OnComplete)

    val uncollectArticle_ =
        uncollectArticle.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    fun loadBinnerInfo() {
        mRepository.binnerInfo().onEach {
            mBinnerInfos.value = it
        }.launchIn(viewModelScope)
    }

    fun loadHomeInfos(refresh: Boolean) {
        mRepository.homeInfos(refresh).onEach {
            mHomeInfos.value = it
        }.launchIn(viewModelScope)
    }

    fun collection(id: String) {
        mRepository.collection(id).onEach {
            collectAtrticle.value = it
        }.launchIn(viewModelScope)
    }

    fun unCollection(id: String) {
        mRepository.unCollection(id).onEach {
            uncollectArticle.value = it
        }.launchIn(viewModelScope)
    }
}