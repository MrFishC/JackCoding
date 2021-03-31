package cn.jack.library_arouter.router;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-16
 * describe:
 */
public class RouterPathActivity {

    public static class Home {
        private static final String HOME = "/home";
        public static final String PAGER_HOME = HOME + "/Home";
    }

    public static class Login {
        private static final String LOGIN = "/login";
        public static final String PAGER_LOGIN = LOGIN + "/Login";
    }

    public static class Register {
        private static final String REGISTER = "/register";
        public static final String PAGER_REGISTER = REGISTER + "/Register";
    }

    public static class Subject {
        private static final String SUBJECT = "/subject";
        public static final String PAGER_SUBJECT = SUBJECT + "/Subject";
    }
}
