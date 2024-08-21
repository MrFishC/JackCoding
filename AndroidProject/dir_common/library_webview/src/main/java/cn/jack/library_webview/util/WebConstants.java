package cn.jack.library_webview.util;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:33
 * @描述
 */
public class WebConstants {

    public static class C_CONFIG{
        public static final String URL = "url";
    }

    public static class C_COMMAND{
        //h5跟native约定好
        public static final String C_COMMAND_0X001 = "showToast";
        public static final String C_COMMAND_0X002 = "showDialog";
        public static final String C_COMMAND_0X003 = "appDataProvider";
        public static final String C_COMMAND_0X004 = "update_title";
        public static final String C_COMMAND_0X005 = "appLogin";
        public static final String C_COMMAND_0X006 = "alert";
        public static final String C_COMMAND_0X007 = "appAlert";
    }
}
