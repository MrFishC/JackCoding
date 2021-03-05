package jack.retrofit2_rxjava2.exception;

import java.io.IOException;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-7
 * describe:token失效(帐号在其它设备登录/或其它原因导致token失效,其它因素导致token失效),抛出异常.
 *
 * 不同的公司项目,该位置的细节性逻辑会不相同.
 */
public class TokenInvalidException extends IOException {

    private int errStatus;
    private String errMessage;

    public TokenInvalidException(int errStatus, String errMessage) {

        this.errStatus = errStatus;
        this.errMessage = errMessage;
    }

    public int getErrorStatus() {
        return errStatus;
    }

    public String getErrorMessage() {
        return errMessage;
    }

}

