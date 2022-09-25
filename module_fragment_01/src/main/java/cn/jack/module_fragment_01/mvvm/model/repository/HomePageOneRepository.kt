package cn.jack.module_fragment_01.mvvm.model.repository

import cn.jack.module_fragment_01.api.ApiService
import cn.jack.module_fragment_01.mvvm.model.entity.HomeInfos
import com.jack.lib_wrapper_mvvm.base.model.BaseWrapperModel
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import com.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 15:59
 * @描述
 */
class HomePageOneRepository @Inject constructor() : BaseWrapperModel() {
    private var mPage = 0

    fun homeInfos(refresh: Boolean) = FlowManager.httpRequest<HomeInfos> {
        if (refresh) {
            mPage = 0
        } else {
            mPage++
        }

        val articleFlow =
            HttpManager.obtainRetrofitService(ApiService::class.java).homeArticleList(mPage)
        val bannerFlow =
            HttpManager.obtainRetrofitService(ApiService::class.java).bannerInfos()

        articleFlow.zip(bannerFlow) { articleData, bannerData ->
            if (articleData.errorCode == 0 && articleData.errorCode == 0) {
                EventResult.OnNext(HomeInfos(bannerData.data, articleData.data))
            } else {
                if (articleData.errorCode != 0) {
                    EventResult.OnFail(Throwable(articleData.errorMsg))
                } else
                    EventResult.OnFail(Throwable(bannerData.errorMsg))
            }
        }
    }
}