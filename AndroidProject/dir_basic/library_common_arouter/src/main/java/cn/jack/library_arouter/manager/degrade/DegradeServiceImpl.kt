package cn.jack.library_arouter.manager.degrade

import android.content.Context
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.library_util.LogU
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 全局降级服务,当路由的时候，目标页不存在，此时重定向到统一错误页
 *
 * 使用需要注意的细节，arouter的相关配置一定要正确。同时观察是否有相应的文件产生（运行后没有产生跟该类有关的文件，说明配置存在错误）。
 */
@Route(path = DegradeServiceImplPath.PATH)
open class DegradeServiceImpl : DegradeService {
    override fun onLost(context: Context?, postcard: Postcard?) {
        LogU.d("全局降级策略 onLost")
        ARouter.getInstance().build(RouterPathActivity.Global.PAGER_GLOBAL).greenChannel().navigation()
    }

    override fun init(context: Context?) {
        LogU.d("全局降级策略 init")
    }
}

object DegradeServiceImplPath {
    const val PATH = "/service/DegradeServiceImpl"
}