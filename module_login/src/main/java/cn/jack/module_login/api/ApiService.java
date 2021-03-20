package cn.jack.module_login.api;

import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import io.reactivex.Observable;
import jack.retrofit2_rxjava2.model.ApiResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:53
 * @描述
 */
public interface ApiService {
    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<ApiResponse<UserInfo>> login(@Field("username") String username, @Field("password") String password);
}
