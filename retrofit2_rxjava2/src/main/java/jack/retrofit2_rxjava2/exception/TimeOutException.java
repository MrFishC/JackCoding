package jack.retrofit2_rxjava2.exception;

import java.io.IOException;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-7
 * describe:连接超时
 */
public class TimeOutException extends IOException {

    private int errStatus;
    private String errMessage;

    public TimeOutException(int errStatus, String errMessage) {

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

