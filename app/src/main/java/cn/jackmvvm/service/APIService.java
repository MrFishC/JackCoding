package cn.jackmvvm.service;

import io.reactivex.Observable;
import jack.retrofit2_rxjava2.model.ApiResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:登录模块的APIService
 */
public interface APIService {

    //get请求
    @GET("a1/b1/cde")
    Observable<ApiResponse<String>> getInfos();

    //post请求
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("a1/b1/cde")
    Observable<ApiResponse<String>> postInfos(@Body RequestBody route);

}
