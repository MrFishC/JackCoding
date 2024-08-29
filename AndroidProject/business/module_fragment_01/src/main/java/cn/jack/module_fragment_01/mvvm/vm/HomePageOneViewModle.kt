package cn.jack.module_fragment_01.mvvm.vm

import androidx.lifecycle.viewModelScope
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos
import cn.jack.module_fragment_01.mvvm.model.repository.HomePageOneRepository
import com.jack.lib_wrapper_mvvm.mvvm.viewmodel.BaseWrapperViewModel
import cn.jack.lib_wrapper_net.model.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
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

    //官方文档，（初次学习的情况下）一如既往的理解不太顺畅（真正理解之后才感觉还算清晰），但是必须要看 https://developer.android.com/kotlin/flow/stateflow-and-sharedflow?hl=zh-cn#stateflow
    //知乎，谷歌开发者 https://zhuanlan.zhihu.com/p/401508806#:~:text=WhileSubscribed%20%E5%85%B1%E4%BA%AB%E7%AD%96%E7%95%A5%E7%94%A8%E4%BA%8E%E5%9C%A8%E6%B2%A1%E6%9C%89%E6%94%B6%E9%9B%86%E8%80%85%E6%97%B6%E5%8F%96%E6%B6%88%E4%B8%8A%E6%B8%B8%E6%95%B0%E6%8D%AE%E6%B5%81%E3%80%82%20%E8%BF%99%E6%A0%B7%E4%B8%80%E6%9D%A5%EF%BC%8C%E6%88%91%E4%BB%AC%E4%BE%BF%E8%83%BD%E5%9C%A8%E6%B2%A1%E6%9C%89%E7%A8%8B%E5%BA%8F%E5%AF%B9%E4%BD%8D%E7%BD%AE%E6%9B%B4%E6%96%B0%E6%84%9F%E5%85%B4%E8%B6%A3%E6%97%B6%E9%81%BF%E5%85%8D%E8%B5%84%E6%BA%90%E7%9A%84%E6%B5%AA%E8%B4%B9%E3%80%82%20Android%20%E5%BA%94%E7%94%A8%E5%B0%8F%E6%8F%90%E9%86%92%EF%BC%81,%E5%9C%A8%E5%A4%A7%E9%83%A8%E5%88%86%E6%83%85%E5%86%B5%E4%B8%8B%EF%BC%8C%E6%82%A8%E5%8F%AF%E4%BB%A5%E4%BD%BF%E7%94%A8%20WhileSubscribed%20%285000%29%EF%BC%8C%E5%BD%93%E6%9C%80%E5%90%8E%E4%B8%80%E4%B8%AA%E6%94%B6%E9%9B%86%E8%80%85%E6%B6%88%E5%A4%B1%E5%90%8E%E5%86%8D%E4%BF%9D%E6%8C%81%E4%B8%8A%E6%B8%B8%E6%95%B0%E6%8D%AE%E6%B5%81%E6%B4%BB%E8%B7%83%E7%8A%B6%E6%80%81%205%20%E7%A7%92%E9%92%9F%E3%80%82
    //创建StateFlow，使用场景跟Livedata很类似          StateFlow 是 SharedFlow 的一种特殊配置
    //StateFlow(Flow的子类) 特点: 必须要有初始值;始终有值；值唯一；可以被多个观察者共订阅；只会把最新的值给订阅者；
    private val mHomeInfos =
        MutableStateFlow<EventResult<List<ArticleInfo>>>(EventResult.OnComplete)

    //SharedFlow 特点：没有默认值；可以保存旧的数据，可以将过去已经更新的值同步给新的订阅者；
    //SharedFlow只能接收流，不能自由取值（StateFlow可以自由取值）
    //参数2： 英文文档解释[通过工具翻译或自己理解出来的好生硬，太难理解，谷歌开发者-知乎文章秒懂] https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-while-subscribed.html
        //copy from start ---> 谷歌开发者-知乎
            //WhileSubscribed：共享策略用于在没有收集者时取消上游数据流。这样一来，我们便能在没有程序对位置更新感兴趣时避免资源的浪费
            //Android 应用小提醒！
                // 在大部分情况下，您可以使用 WhileSubscribed(5000)，当最后一个收集者消失后再保持上游数据流活跃状态 5 秒钟。
                // 这样在某些特定情况 (如配置改变) 下可以避免重启上游数据流。当上游数据流的创建成本很高，或者在 ViewModel 中使用这些操作符时，这一技巧尤其有用。
        //copy from end ---> 谷歌开发者-知乎
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
//            println("查看线程信息 5 ${Thread.currentThread().name}")
            mHomeInfos.value = it
        }.launchIn(viewModelScope) //viewModelScope:协程作用域，其CoroutineDispatcher默认式Dispatchers.Main
        //launchIn：下游终止操作符，将上游代码分发到指定的线程   其本质是调用了collect()
        //在这里onEach所在的线程由launchIn操作符决定
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