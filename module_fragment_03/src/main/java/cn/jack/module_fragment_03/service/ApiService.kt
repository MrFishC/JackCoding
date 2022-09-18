package cn.jack.module_fragment_03.service

import cn.jack.module_fragment_03.entity.NavInfo
import cn.jack.module_fragment_03.entity.SystemInfo
import jack.retrofit2_rxjava2.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("navi/json")
    fun systemInfoList(): Flow<ApiResponse<List<NavInfo>>>

    /**
     * 体系
     */
    @GET("tree/json")
    fun squareInfoList(): Flow<ApiResponse<List<SystemInfo>>>
}