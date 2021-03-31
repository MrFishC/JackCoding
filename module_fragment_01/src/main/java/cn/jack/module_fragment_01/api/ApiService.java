package cn.jack.module_fragment_01.api;

import java.util.List;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos;
import io.reactivex.Observable;
import jack.retrofit2_rxjava2.model.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:53
 * @描述
 */
public interface ApiService {
    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    Observable<ApiResponse<ProjectInfoList>> homeArticleList(@Path("page") int page);

    /**
     * 轮播图信息
     */
    @GET("banner/json")
    Observable<ApiResponse<List<BanInfos>>> bannerInfos();
}
