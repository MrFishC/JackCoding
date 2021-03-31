package jack.retrofit2_rxjava2.manager.rx;

import java.io.IOException;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.exception.DataNullException;
import jack.retrofit2_rxjava2.exception.TimeOutException;
import jack.retrofit2_rxjava2.exception.UnloginException;
import jack.retrofit2_rxjava2.model.ApiResponse;
import jack.retrofit2_rxjava2.util.net.NetConfig;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:将ApiResponse<T>转换成T格式
 *
 * 数据剥壳：同时对stat的不同状态进行处理
 *
 * （实践发现的）问题：在飞行模式的情况下，RxFunction的代码不执行。具体原因不清楚(夜神模拟器和手机均测试)
 */
public final class RxFunction<T> implements Function<ApiResponse<T>, T> {

    @Override
    public T apply(@NonNull ApiResponse<T> apiResponse) throws IOException {
            int status = apiResponse.getErrorCode();

            System.out.println(" RxFunction status " + status);

            if(status == NetConfig.CODE_ERROR || status == NetConfig.NOT_MATCH){
                System.out.println(" RxFunction " + apiResponse.getErrorMsg());
                throw new ApiException(apiResponse.getErrorCode(),apiResponse.getErrorMsg());
            }else if(status == NetConfig.UN_LOGIN){
                System.out.println(" RxFunction " + apiResponse.getErrorMsg());
                throw new UnloginException(apiResponse.getErrorCode(),apiResponse.getErrorMsg());
            }else if(status == NetConfig.CODE_SUCCESS){
                if (apiResponse.getData() == null) {
                    System.out.println(" RxFunction " + apiResponse.getErrorMsg());
                    throw new DataNullException(apiResponse.getErrorCode(),apiResponse.getErrorMsg());
                }
                return apiResponse.getData();
            }else {
                System.out.println(" RxFunction " + apiResponse.getErrorMsg());
                throw new TimeOutException(apiResponse.getErrorCode(), "请求超时");
            }

    }

}
