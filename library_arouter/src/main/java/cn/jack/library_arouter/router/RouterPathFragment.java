package cn.jack.library_arouter.router;

/**
 * created by Jack
 * email:yucrun@163.com
 * describe:
 */
public class RouterPathFragment {

    /**
     * module-fragment-01
     */
    public static class HomeFirst {
        private static final String HOME_FIRST = "/home_first";
        /*首页-home-first*/
        public static final String PAGER_HOME_FIRST = HOME_FIRST + "/Home_First";
    }

    /**
     * module-fragment-02
     */
    public static class HomeSecond {
        private static final String HOME_SECOND = "/home_second";
        /*首页-home_second*/
        public static final String PAGER_HOME_SECOND = HOME_SECOND + "/Home_Second";
    }

    /**
     * module-fragment-03
     */
    public static class HomeThird {
        private static final String HOME_THIRD = "/home_third";
        /*首页-home_third*/
        public static final String PAGER_HOME_THIRD = HOME_THIRD + "/Home_Third";

        private static final String HOME_SQUARE = "/home_square";
        /*首页-home_Square*/
        public static final String PAGER_HOME_SQUARE = HOME_SQUARE + "/Home_Square";

        private static final String HOME_SYSTEM = "/home_system";
        /*首页-home_System*/
        public static final String PAGER_HOME_SYSTEM = HOME_SYSTEM + "/Home_System";
    }

    /**
     * module-fragment-04
     */
    public static class HomeFour {
        private static final String HOME_FOUR = "/home_four";
        /*首页-home_four*/
        public static final String PAGER_HOME_FOUR = HOME_FOUR + "/Home_Four";
    }

}
