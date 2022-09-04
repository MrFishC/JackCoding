package cn.jack.module_login.mvvm.modle.repository

import cn.jack.module_login.api.ApiService
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import com.jack.lib_wrapper_mvvm.base.model.BaseWrapperModel
import com.jack.lib_wrapper_net.flow.FlowManager
import jack.retrofit2_rxjava2.manager.HttpManager
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 15:00
 * @描述
 */
class RegisterHttpRepository @Inject constructor() : BaseWrapperModel() {

    fun register(
        userName: String,
        passwd: String,
        againPasswd: String
    ) = FlowManager.httpRequestSimple<UserInfo>(
        HttpManager.obtainRetrofitService(ApiService::class.java)
            .register(userName, passwd, againPasswd)
    )
}
