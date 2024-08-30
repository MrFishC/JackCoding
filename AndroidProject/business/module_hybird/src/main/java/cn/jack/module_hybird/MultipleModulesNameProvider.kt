package cn.jack.module_hybird

import cn.jack.lib_common.flutter.cache.ModuleNameProvider

/**
 * @创建者 Jack
 * @创建时间 2024-04-27 10:51
 * @描述 实现 ModuleNameProvider，用于获取多个模块名称
 * 可以作为业务模块 依赖的上层业务模块
 */
class MultipleModulesNameProvider : ModuleNameProvider {
    override fun getModuleNames(): List<String> {
        return listOf(MODULE_NAME_COLLECTION) //模块名称列表
    }

    companion object {
        //根据业务，自定义需要的字符串即可
        const val MODULE_NAME_COLLECTION: String = "collection"
        const val MODULE_NAME_MALL: String = "mall"
        const val MODULE_NAME_MINE: String = "mine"


        //约定好的事件
        const val POST_COOKIE: String = "postCookie"
    }
}
