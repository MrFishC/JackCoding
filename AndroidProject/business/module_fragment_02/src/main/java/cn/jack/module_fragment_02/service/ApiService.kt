package cn.jack.module_fragment_02.service

import cn.jack.library_common_business.entiy.ProjectInfoList
import cn.jack.module_fragment_02.entiy.SortInfo
import com.jack.lib_wrapper_net.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * 项目信息列表
     */
    @GET("project/list/{page}/json")
    fun listProjects(
        @Path("page") page: Int,
        @Query("cid") id: Int
    ): Flow<ApiResponse<ProjectInfoList>>

    @GET("project/tree/json")
    fun projectSortInfos(): Flow<ApiResponse<List<SortInfo>>>
}