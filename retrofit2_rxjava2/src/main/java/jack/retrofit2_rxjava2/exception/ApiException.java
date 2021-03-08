package jack.retrofit2_rxjava2.exception;

import java.io.IOException;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:
 */
public class ApiException extends IOException {

    private int errStatus;
    private String errMessage;

    public ApiException(int errStatus, String errMessage) {
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