package jack.wrapper.constant;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-6
 * describe:存放常量
 */
public class SpConstant {

    /**
     * user
     */
    public String USER_NAME = "user_name";
    public String PASS_WD = "pass_wd";
    public String TOKEN = "token";









    /* todo ------------------------------------------------- 单例: 华丽的分割线 -------------------------------------------------------------*/
    private SpConstant() {

    }

    public static SpConstant getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SpConstant INSTANCE = new SpConstant();
    }
}
