package cn.jack.module_login.api

import cn.jack.module_login.mvvm.modle.entity.UserInfo
import com.jack.lib_wrapper_net.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:53
 * @描述
 */
interface ApiService {
    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ):Flow<ApiResponse<UserInfo>>

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Flow<ApiResponse<UserInfo>>
}