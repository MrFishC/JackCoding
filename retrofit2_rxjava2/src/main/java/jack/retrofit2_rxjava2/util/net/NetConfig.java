package jack.retrofit2_rxjava2.util.net;

/**
 * @author Jack
 * @time 19-11-29 下午8:53
 * @describe 具体值跟后台协商
 */
public class NetConfig {

    /**
     * 正常
     */
    public static final int OK = 1;
    /**
     * 失败
     */
    public static final int FAIL = -1;
    /**
     * token失效
     */
    public static final int TOKEN_INVALID = 999;
    /**
     * 密码为初始化密码，需要修改密码
     */
    public static final int UPDATE_PASSWD = 998;
    /**
     * 密码失效，需要修改
     */
    public static final int PASSWD_INVALID = 997;
    /**
     * 超时
     */
    public static final int TIME_OUT = 500;

}
