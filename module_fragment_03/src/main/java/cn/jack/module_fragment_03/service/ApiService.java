package cn.jack.module_fragment_03.service;

import java.util.List;

import cn.jack.module_fragment_03.entity.SystemAndSquareInfo;
import io.reactivex.Observable;
import jack.retrofit2_rxjava2.model.ApiResponse;
import retrofit2.http.GET;

public interface ApiService {

    @GET("navi/json")
    Observable<ApiResponse<List<SystemAndSquareInfo>>> getSystemAndSquareInfoList();

}
