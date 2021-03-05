package jack.retrofit2_rxjava2.manager.rx;


import java.io.IOException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import jack.retrofit2_rxjava2.exception.DataNullException;
import jack.retrofit2_rxjava2.exception.TimeOutException;
import jack.retrofit2_rxjava2.exception.TokenInvalidException;
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
 */
public final class RxFunction<T> implements Function<ApiResponse<T>, T> {

    @Override
    public T apply(@NonNull ApiResponse<T> apiResponse) throws IOException {

            int status = apiResponse.getStat();

//            if(status == NetConfig.FAIL){
//                throw new ApiException(apiResponse.getStat(), apiResponse.getMsg());
//            }else

            if(status == NetConfig.TOKEN_INVALID){
                throw new TokenInvalidException(apiResponse.getStat(),apiResponse.getMsg());
            }else if(status == NetConfig.OK){
                if (apiResponse.getData() == null) {
                    throw new DataNullException(apiResponse.getStat(),apiResponse.getMsg());
                }
                return apiResponse.getData();
            }else {
                throw new TimeOutException(apiResponse.getStat(), "请求超时");
            }

    }

}
