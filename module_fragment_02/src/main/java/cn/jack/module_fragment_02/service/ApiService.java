package cn.jack.module_fragment_02.service;

import cn.jack.module_fragment_02.entiy.ArticleListRes;
import io.reactivex.Observable;
import jack.retrofit2_rxjava2.model.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    /**
     * 项目列表
     *
     * @param id   分类id
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<ApiResponse<ArticleListRes>> listProjects(@Path("page") int page, @Query("cid") String id);

}
