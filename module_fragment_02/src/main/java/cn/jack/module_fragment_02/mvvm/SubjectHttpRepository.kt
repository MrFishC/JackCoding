package cn.jack.module_fragment_02.mvvm

import cn.jack.library_common_business.entiy.ProjectInfoList
import cn.jack.library_common_business.service.ApiArticleService
import com.jack.lib_wrapper_mvvm.mvvm.model.BaseWrapperModel
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import com.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @创建者 Jack
 * @创建时间 2021/3/30 16:56
 * @描述
 */
class SubjectHttpRepository @Inject constructor(): BaseWrapperModel() {
    private var mPage = 0

    fun pageSubject(refresh: Boolean, articleId: Int) =
        FlowManager.httpRequest<ProjectInfoList> {
            if (refresh) {
                mPage = 0
            } else {
                mPage++
            }

            HttpManager.obtainRetrofitService(ApiArticleService::class.java)
                .pageArticleList(mPage, articleId)
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnFail(Throwable(it.errorMsg))
                    }
                }
        }
}