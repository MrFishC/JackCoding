package cn.jack.module_fragment_01.mvvm.model.repository

import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.library_common_business.service.ApiArticleService
import cn.jack.module_fragment_01.api.ApiService
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos
import cn.jack.module_fragment_01.mvvm.model.entity.HomeInfos
import com.jack.lib_wrapper_mvvm.mvvm.model.BaseWrapperModel
import cn.jack.lib_wrapper_net.flow.FlowManager
import cn.jack.lib_wrapper_net.manager.HttpManager
import cn.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 15:59
 * @描述
 */
class HomePageOneRepository @Inject constructor() : BaseWrapperModel() {
    fun binnerInfo() = FlowManager.httpRequestSimple<List<BanInfos>>(
        HttpManager.obtainRetrofitService(ApiService::class.java)
            .bannerInfos()
    )

    private var mPage = 0
    fun homeInfos(refresh: Boolean) = FlowManager.httpRequest<List<ArticleInfo>> {
        if (refresh) {
            mPage = 0
        } else {
            mPage++
        }

        val topArticFlow =
            HttpManager.obtainRetrofitService(ApiService::class.java).topArticles()
        val articleFlow =
            HttpManager.obtainRetrofitService(ApiService::class.java).homeArticleList(mPage)

        articleFlow.zip(topArticFlow) { articleFlow, topArticFlow ->
            if (topArticFlow.errorCode == 0 && articleFlow.errorCode == 0) {
                topArticFlow.data?.let {
                    articleFlow.data?.datas?.addAll(0, it)
                }
                EventResult.OnNext(articleFlow.data?.datas)     //zip内部最终会将结果发送到下游（查看map操作符更容易分析）
            } else {
                if (articleFlow.errorCode != 0) {
                    EventResult.OnFail(Throwable(articleFlow.errorMsg))
                } else
                    EventResult.OnFail(Throwable(topArticFlow.errorMsg))
            }
        }
    }

    fun collection(id: String) =
        FlowManager.httpRequestSimple<String>(
            HttpManager.obtainRetrofitService(ApiArticleService::class.java)
                .collectAtrticle(id)
        )

    fun unCollection(id: String) =
        FlowManager.httpRequestSimple<String>(
            HttpManager.obtainRetrofitService(ApiArticleService::class.java)
                .unCollectAtrticle(id)
        )
}