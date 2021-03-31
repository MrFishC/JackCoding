package jack.retrofit2_rxjava2.manager.rx;

import java.util.concurrent.TimeoutException;

import jack.retrofit2_rxjava2.exception.TimeOutException;
import jack.retrofit2_rxjava2.exception.TokenInvalidException;
import jack.retrofit2_rxjava2.exception.UnloginException;

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 14:08
 * @描述 异常处理     基于RxFunction的封装进行处理
 */
public class RxExceptionManager {

    public static RxExceptionManager getInstance() {
        return RxExceptionManager.Holder.INSTANCE;
    }

    private static class Holder {
        private static final RxExceptionManager INSTANCE = new RxExceptionManager();
    }

    private RxExceptionManager() {

    }

    public void exceptionHandler(Throwable e){

        if (e instanceof UnloginException) {
            //发送广播(使用事件总线来代替)
            System.out.println(" 未登录异常");
        }else if (e instanceof TokenInvalidException) {
            System.out.println(" token失效");
        }else if (e instanceof TimeOutException) {
            System.out.println(" 超时");
        }else {
            System.out.println(" 其它异常 " + e.getMessage());
        }

    }
}
