package jack.retrofit2_rxjava2.exception;

import java.io.IOException;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:
 */
public class NetErrorException extends IOException {

    private String errMessage;

    public NetErrorException(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrorMessage() {
        return errMessage;
    }

}