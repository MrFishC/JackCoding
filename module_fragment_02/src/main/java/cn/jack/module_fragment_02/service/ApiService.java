package cn.jack.module_fragment_02.service;

import cn.jack.library_common_business.entiy.ProjectInfoList;
import io.reactivex.Observable;
import jack.retrofit2_rxjava2.model.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    /**
     * 项目信息列表
     */
    @GET("project/list/{page}/json")
    Observable<ApiResponse<ProjectInfoList>> listProjects(@Path("page") int page, @Query("cid") String id);

}
