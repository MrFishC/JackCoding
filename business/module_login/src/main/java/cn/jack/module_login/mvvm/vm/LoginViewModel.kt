package cn.jack.module_login.mvvm.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import cn.jack.module_login.mvvm.modle.repository.LoginHttpRepository
import com.jack.lib_base.base.vm.BaseViewModle
import com.jack.lib_wrapper_net.model.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:45
 * @描述
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val mRepository: LoginHttpRepository) :
    BaseViewModle() {

    //StateFlow的使用场景与LiveData是非常接近的
    //与LiveData组件不同的是，这里开发者必须为MutableFlow指定默认值。
    //StateFlow 是 SharedFlow 的一个特殊变种，StateFlow 与 LiveData 是最接近的，推出就是为了替换LiveData
    //特性：
    //1.它始终是有值的，StateFlow需要一个初始值，而LiveData不需要。value空安全
    //2.它的值是唯一的。
    //3.它允许被多个观察者共用 (因此是共享的数据流)。
    //4.它永远只会把最新的值重现给订阅者，这与活跃观察者的数量是无关的。
    private val userInfoState = MutableStateFlow<EventResult<UserInfo>>(EventResult.OnComplete)

    //使用Flow来监听，替换LiveData
    val userInfo =
        userInfoState.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    var mPhone = ObservableField<String>()

    var mPasswd = ObservableField<String>()

    fun userLogin(phone: String, passwd: String) {
        mRepository.login(phone, passwd)
            .onEach {
                userInfoState.value = it
            }.launchIn(viewModelScope)
    }

}