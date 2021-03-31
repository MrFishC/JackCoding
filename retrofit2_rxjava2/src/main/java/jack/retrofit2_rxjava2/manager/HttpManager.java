package jack.retrofit2_rxjava2.manager;

import androidx.annotation.Nullable;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import cn.jack.library_util.AppContext;
import cn.jack.library_util.LogUtils;
import jack.retrofit2_rxjava2.interceptor.TokenInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:
 */

public class HttpManager {
    /**
     * 构建OkHttp
     */
    private static final class OKHttpHolder {

        private static final int                  TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER  = new OkHttpClient.Builder();

        private static OkHttpClient.Builder addInterceptor() {
            //debug模式下,不添加日志拦截器
//            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor(
                        new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(@Nullable String message) {
                                LogUtils.i("HttpLog",message);
                            }
                        }
                );
                loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                BUILDER.addInterceptor(loggerInterceptor);
//            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new TokenInterceptor())         //头部统一添加token
                .cookieJar(new PersistentCookieJar(new SetCookieCache(),
                        new SharedPrefsCookiePersistor(AppContext.getContext())))
                .build();
    }

    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = "https://www.wanandroid.com/";

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())                   // 添加Rxjava2适配器
                .addConverterFactory(GsonConverterFactory.create())                          // 添加Gson转换器           //todo fastjson转换器待封装
                .build();
    }

    /**
     * 对外提供公共的访问方法
     *
     * @return Retrofit
     */
    public static Retrofit getRetrofitClient() {
        return RetrofitHolder.RETROFIT_CLIENT;
    }

}
