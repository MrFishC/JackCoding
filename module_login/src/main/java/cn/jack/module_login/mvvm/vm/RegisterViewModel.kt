package cn.jack.module_login.mvvm.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import cn.jack.module_login.mvvm.modle.repository.RegisterHttpRepository
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
 * @创建时间 2021/3/24 15:00
 * @描述
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(private val mRepository: RegisterHttpRepository):
    BaseWrapperViewModel() {
    private val TAG = RegisterViewModel::class.java.name

    private val registerUserInfoState = MutableStateFlow<EventResult<UserInfo>>(EventResult.OnComplete)

    val registerUserInfo =
        registerUserInfoState.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    var mPhone = MutableLiveData<String>()
    var mPasswd = MutableLiveData<String>()
    var mPasswdAgain = MutableLiveData<String>()

    fun registerUser() {
        viewModelScope.launch {
            Log.d(TAG, "registerUser: ")
//            mRepository.register(mPhone.value!!, mPasswd.value!!, mPasswdAgain.value!!).onEach {
//                registerUserInfoState.value = it
//                println("===> " + it + " " + registerUserInfoState.value)
//            }

            mRepository.register1("13611113310", "123456", "1234567")
        }
    }
}