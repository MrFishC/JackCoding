package jack.retrofit2_rxjava2.model;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:服务器返回的json数据转化成bean格式;
 */
public class ApiResponse<T extends Object> {

    private String code;
    private int stat;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
            this.data = data;
    }

    public boolean isOk() {
        return stat == 1;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code='" + code + '\'' +
                ", stat=" + stat +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
