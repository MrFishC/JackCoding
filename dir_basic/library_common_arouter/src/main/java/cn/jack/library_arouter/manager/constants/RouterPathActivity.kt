package cn.jack.library_arouter.manager.constants

/**
 * created by Jack
 * date:19-4-16
 * describe:
 */
class RouterPathActivity {
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
}