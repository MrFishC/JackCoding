package cn.jack.library_common_business.service;

import cn.jack.library_common_business.entiy.ProjectInfoList;
import io.reactivex.Observable;
import jack.retrofit2_rxjava2.model.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiArticleService {

    /**
     * 列表-我的收藏
     */
    @GET("lg/collect/list/{page}/json")
    Observable<ApiResponse<ProjectInfoList>> getArticleCollection(@Path("page") int page);

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    Observable<ApiResponse<String>> collectAtrticle(@Path("id") String id);

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ApiResponse<String>> unCollectAtrticle(@Path("id") String id);

    /**
     * 知识体系下的文章
     */
    @GET("article/list/{page}/json")
    Observable<ApiResponse<ProjectInfoList>> pageArticleList(@Path("page") int page, @Query("cid") String id);
}
