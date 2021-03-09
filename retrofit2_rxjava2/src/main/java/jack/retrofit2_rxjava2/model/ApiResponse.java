package jack.retrofit2_rxjava2.model;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:服务器返回的json数据转化成bean格式;
 */
public class ApiResponse<T extends Object> {

    private int errorCode;          //状态码
    private String errorMsg;        //信息
    private T data;                 //数据

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

}
