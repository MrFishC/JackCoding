package cn.jack.module_login.mvvm.modle.repository

import android.util.Log
import cn.jack.module_login.api.ApiService
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import com.jack.lib_wrapper_mvvm.base.model.BaseWrapperModel
import com.jack.lib_wrapper_net.flow.EventResult
import com.jack.lib_wrapper_net.flow.FlowManager
import io.reactivex.schedulers.Schedulers
import jack.retrofit2_rxjava2.manager.HttpManager
import jack.retrofit2_rxjava2.manager.rx.RxFunction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 15:00
 * @描述
 */
class RegisterHttpRepository @Inject constructor() : BaseWrapperModel() {
    private val TAG = RegisterHttpRepository::class.java.name

    suspend fun register1(
        userName: String,
        passwd: String,
        againPasswd: String
    ) {
        Log.d(TAG, "register: ")
        val apiResponse =
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .register(userName, passwd, againPasswd)

        println("apiResponse ---->>>")
    }

    suspend fun register(
        userName: String,
        passwd: String,
        againPasswd: String
    ): Flow<EventResult<UserInfo>> =
        FlowManager<UserInfo>().httpRequest {
            flow<EventResult<UserInfo>> {

                Log.d(TAG, "register: ")

                    HttpManager.obtainRetrofitService(ApiService::class.java)
                        .register(userName, passwd, againPasswd)

                    //通过一系列操作符处理数据，如map，如果有必要的话
//                    .map {
//                        // ...
//                    }
                    //在Dispatcher.IO上下文中产生订阅数据
//                    .flowOn(Dispatchers.IO)
                    //捕获异常
//                    .catch { ex ->
//                        //处理异常
//                        println("error occurs:$ex")
//                    }
                    //订阅数据
                    .collect {
                        //处理数据
                        println("weather info:$it")
                    }

                println("apiResponse ---->>>")
//                emit(EventResult.OnNext(apiResponse.data!!))
            }
        }

    fun test() {
//        RxUtils.getInstance()
//                .obtainRetrofitService(ApiService::class.java)
//                .register("","","")
//                .subscribeOn(Schedulers.io())
//                .map(RxFunction())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxBaseSubscriber<UserInfo>(this) {
//
//                    @Override
//                    public void onFailed(ErrorStatusInfo errorStatusInfo) {
//                        iRegisterLisenter.registerFailed(errorStatusInfo.message);
//                    }
//
//                    @Override
//                    public void onSuccess(UserInfo userInfo) {
//                        SPUtils.getInstance().putData(C.C_USER_NAME,userName);
//                        SPUtils.getInstance().putData(C.C_USER_PASSWD,passwd);
//                        iRegisterLisenter.registerSuccess(userInfo);
//                    }
//                });
    }

}