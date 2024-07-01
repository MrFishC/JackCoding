package cn.jack.library_arouter.manager.service

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @创建者 Jack
 * @创建时间 2024-06-10 16:44
 * @描述
 */
object LoginServiceProvider {
//    private val iLoginService =
//        ARouter.getInstance().build(RouterPathService.LoginS.PAGER_LOGINS).navigation() as? ILoginService
    private val iLoginService =
        ARouter.getInstance().build(LoginServiceImplPath.PATH).navigation() as? ILoginService
    fun login(context: Context?) {
        iLoginService?.login(context)
    }

    fun isLogin(): Boolean {
        return iLoginService?.isLogin() == true
    }
}