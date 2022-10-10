package cn.jack.library_arouter.router

/**
 * created by Jack
 * describe:
 */
class RouterPathFragment {
    /**
     * module-fragment-01
     */
    object HomeFirst {
        private const val HOME_FIRST = "/home_first"

        /*首页-home-first*/
        const val PAGER_HOME_FIRST = HOME_FIRST + "/Home_First"
    }

    /**
     * module-fragment-02
     */
    object HomeSecond {
        private const val HOME_SECOND = "/home_second"

        /*首页-home_second*/
        const val PAGER_HOME_SECOND = HOME_SECOND + "/Home_Second"
    }

    /**
     * module-fragment-03
     */
    object HomeThird {
        private const val HOME_THIRD = "/home_third"

        /*首页-home_third*/
        const val PAGER_HOME_THIRD = HOME_THIRD + "/Home_Third"
        private const val HOME_SQUARE = "/home_square"

        /*首页-home_Square*/
        const val PAGER_HOME_SQUARE = HOME_SQUARE + "/Home_Square"
        private const val HOME_SYSTEM = "/home_system"

        /*首页-home_System*/
        const val PAGER_HOME_SYSTEM = HOME_SYSTEM + "/Home_System"
    }

    /**
     * module-fragment-04
     */
    object HomeFour {
        private const val HOME_FOUR = "/home_four"

        /*首页-home_four*/
        const val PAGER_HOME_FOUR = HOME_FOUR + "/Home_Four"
    }
}