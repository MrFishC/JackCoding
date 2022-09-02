package cn.jack.module_login.mvvm.modle.repository

import cn.jack.module_login.api.ApiService
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import com.jack.lib_wrapper_mvvm.base.model.BaseWrapperModel
import com.jack.lib_wrapper_net.flow.EventResult
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.retrofit.RequestManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:41
 * @描述  研究一下retrofit自定义转换器的方案实现
 */
class LoginHttpRepository @Inject constructor() : BaseWrapperModel() {

    suspend fun login(userName: String?, passwd: String?): Flow<EventResult<UserInfo>> =
        FlowManager<UserInfo>().httpRequest {
            flow<EventResult<UserInfo>> {
//                val apiResponse =
                    RequestManager.retrofit.create(ApiService::class.java)
                        .login(userName!!, passwd!!)
//                emit(EventResult.OnNext(apiResponse.data!!))
            }
        }
}