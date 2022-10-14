package cn.jack.module_fragment_03.service

import cn.jack.module_fragment_03.entity.NavInfo
import cn.jack.module_fragment_03.entity.SystemInfo
import com.jack.lib_wrapper_net.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    /**
     * 导航
     */
    @GET("navi/json")
    fun systemInfoList(): Flow<ApiResponse<List<NavInfo>>>

    /**
     * 体系
     */
    @GET("tree/json")
    fun squareInfoList(): Flow<ApiResponse<List<SystemInfo>>>
}