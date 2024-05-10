package cn.jack.library_arouter.manager.constants

/**
 * created by Jack
 * date:19-4-16
 * describe:
 */
class RouterPathActivity {
    object Global {
        private const val GLOBAL = "/global"
        const val PAGER_GLOBAL = "$GLOBAL/activity"
    }

    object Home {
        private const val HOME = "/home"
        const val PAGER_HOME = "$HOME/Home"
    }

    object Login {
        private const val LOGIN = "/login"
        const val PAGER_LOGIN = "$LOGIN/Login"
    }

    object Register {
        private const val REGISTER = "/register"
        const val PAGER_REGISTER = "$REGISTER/Register"
    }

    object Subject {
        private const val SUBJECT = "/subject"
        const val PAGER_SUBJECT = "$SUBJECT/Subject"
    }

    object Web {
        private const val WEB = "/web"
        const val PAGER_WEB = "$WEB/Web"
    }

    object Search {
        private const val RESULT = "/result"
        const val PAGER_RESULT = "$RESULT/Result"
    }

    object SimpleRv {
        private const val SIMPLE_RV = "/simple_rv"
        const val PAGER_SIMPLE_RV = "$SIMPLE_RV/SimpleRv"
    }

    object SimpleCustom {
        private const val SIMPLE_CUSTOM = "/simple_custom"
        const val PAGER_SIMPLE_CUSTOM = "$SIMPLE_CUSTOM/SimpleCustom"
    }

    object MallActivity {
        private const val MALL_ACTIVITY = "/mall_activity"
        const val PAGER_MALL_ACTIVITY = "$MALL_ACTIVITY/MallActivity"
    }
}