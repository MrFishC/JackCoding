package jack.retrofit2_rxjava2.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by Jack
 * email:yucrun@163.com
 * describe:网络请求token的统一添加
 *
 * todo 拦截器的多种用法
 * 1.https://www.jianshu.com/p/eaee7cd227cd
 */
public class TokenInterceptor implements Interceptor {

    private final String TOKEN = "token";
    
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String url = request.url().toString();

        if (needAddToken(url)) {

            String token = "";  //获取本地存储的token

            Request updateRequest = request.newBuilder().header(TOKEN, token).build();
            return chain.proceed(updateRequest);

        }

        return chain.proceed(request);

    }

    /**
     * 不需要添加token的api
     */
    private boolean needAddToken(String url) {
        return  !url.contains("../...") || !url.contains("../...");
    }

}
