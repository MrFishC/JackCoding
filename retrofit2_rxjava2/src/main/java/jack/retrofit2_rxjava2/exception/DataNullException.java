package jack.retrofit2_rxjava2.exception;

import java.io.IOException;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:后台返回的数据格式为null,也就是空数据。可以跟后台协商，不返回空数据。
 */
public class DataNullException extends IOException {

    private int errStatus;
    private String errMessage;

    public DataNullException(int errStatus, String errMessage) {
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