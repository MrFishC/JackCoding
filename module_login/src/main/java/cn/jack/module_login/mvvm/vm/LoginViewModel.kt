package cn.jack.module_login.mvvm.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import cn.jack.module_login.mvvm.modle.repository.LoginHttpRepository
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import com.jack.lib_wrapper_net.flow.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:45
 * @描述
 *
 *
 * https://juejin.cn/post/7064123524587192356
 *
 *
 * 学习其优点 https://juejin.cn/post/6938777676928778247
 * https://juejin.cn/post/7031479214683455502
 * https://juejin.cn/post/7088630212371415076
 *
 * 给力 http://blog.mxnzp.com/?p=209
 *
 *
 *
 * https://juejin.cn/post/7114181318644072479
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val mRepository: LoginHttpRepository) :
    BaseWrapperViewModel() {

    //StateFlow的使用场景与LiveData是非常接近的
    //与LiveData组件不同的是，这里开发者必须为MutableFlow指定默认值。
    //StateFlow 是 SharedFlow 的一个特殊变种，StateFlow 与 LiveData 是最接近的，推出就是为了替换LiveData
    //特性：
    //1.它始终是有值的，StateFlow需要一个初始值，而LiveData不需要。value空安全
    //2.它的值是唯一的。
    //3.它允许被多个观察者共用 (因此是共享的数据流)。
    //4.它永远只会把最新的值重现给订阅者，这与活跃观察者的数量是无关的。
    private val userInfoState = MutableStateFlow<EventResult<UserInfo>>(EventResult.OnStart)

    //使用Flow来监听，替换LiveData
    val userInfo =
        userInfoState.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    @JvmField
    var mPhone = ObservableField<String>()

    //密码
    @JvmField
    var mPasswd = ObservableField<String>()

    fun userLogin() {
        viewModelScope.launch {
            mRepository.login(mPhone.get(), mPasswd.get()).onEach {
                userInfoState.value = it
                println("===> " + it + " " + userInfoState.value)
            }
        }
    }

}