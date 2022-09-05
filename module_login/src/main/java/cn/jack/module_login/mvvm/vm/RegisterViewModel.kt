package cn.jack.module_login.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import cn.jack.module_login.mvvm.modle.repository.RegisterHttpRepository
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import com.jack.lib_wrapper_net.model.EventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 15:00
 * @描述
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterHttpRepository) :
    BaseWrapperViewModel(repository) {

    private val registerUserInfoState =
        MutableStateFlow<EventResult<UserInfo>>(EventResult.OnComplete)

    val registerUserInfo =
        registerUserInfoState.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    var mPhone = MutableLiveData<String>()
    var mPasswd = MutableLiveData<String>()
    var mPasswdAgain = MutableLiveData<String>()

    fun registerUser(phone: String,
                     passwd: String,
                     passwdAgain: String) {

        //这种方式实现 无法调用    具体原因不清楚
//        viewModelScope.launch {
//            repository.register("13611113310", "123456", "1234567")
//                .onEach {
//                    registerUserInfoState.value = it
//                    println("===> " + it + " " + registerUserInfoState.value)
//                }
//        }

        repository.register(phone, passwd, passwdAgain)
            .onEach {
                registerUserInfoState.value = it
            }.launchIn(viewModelScope)
    }
}