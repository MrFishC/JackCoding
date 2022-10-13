package cn.jack.library_common_business.service

import cn.jack.library_common_business.entiy.ApiResponse
import cn.jack.library_common_business.entiy.ProjectInfoList
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiArticleService {
    /**
     * 列表-我的收藏
     */
    @GET("lg/collect/list/{page}/json")
    fun getArticleCollection(@Path("page") page: Int): Flow<ApiResponse<ProjectInfoList>>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    fun collectAtrticle(@Path("id") id: String): Flow<ApiResponse<String>>

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun unCollectAtrticle(@Path("id") id: String): Flow<ApiResponse<String>>

    /**
     * 知识体系下的文章
     */
    @GET("article/list/{page}/json")
    fun pageArticleList(
        @Path("page") page: Int,
        @Query("cid") id: Int
    ): Flow<ApiResponse<ProjectInfoList>>
}