package cn.jack.module_login.mvvm.modle.repository

import cn.jack.library_common_business.constant.C
import cn.jack.library_util.KvStoreUtil
import cn.jack.module_login.api.ApiService
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import com.jack.lib_wrapper_mvvm.mvvm.model.BaseWrapperModel
import com.jack.lib_wrapper_net.model.EventResult
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:41
 * @描述  研究一下retrofit自定义转换器的方案实现
 */
class LoginHttpRepository @Inject constructor() : BaseWrapperModel() {

    fun login(userName: String, passwd: String) =
        FlowManager.httpRequest<UserInfo> {
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .login(userName, passwd)
                .map {
                    if (it.errorCode == 0) {
                        KvStoreUtil.getInstance().save(C.C_USER_NAME,userName)
                        KvStoreUtil.getInstance().save(C.C_USER_PASSWD,passwd)
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnFail(Throwable(it.errorMsg))
                    }
                }
        }
}