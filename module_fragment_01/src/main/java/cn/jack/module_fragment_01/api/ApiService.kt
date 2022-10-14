package cn.jack.module_fragment_01.api

import cn.jack.library_common_business.entiy.ProjectInfoList
import retrofit2.http.GET
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos
import com.jack.lib_wrapper_net.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:53
 * @描述
 */
interface ApiService {
    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    fun homeArticleList(@Path("page") page: Int): Flow<ApiResponse<ProjectInfoList>>

    /**
     * 轮播图信息
     */
    @GET("banner/json")
    fun bannerInfos(): Flow<ApiResponse<List<BanInfos>>>
}