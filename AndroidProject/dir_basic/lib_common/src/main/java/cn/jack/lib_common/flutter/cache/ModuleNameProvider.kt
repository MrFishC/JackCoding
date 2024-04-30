package cn.jack.lib_common.flutter.cache

/**
 * @创建者 Jack
 * @创建时间 2024-04-27 10:51
 * @描述
 */
// 定义一个接口，用于获取多个模块名称
interface ModuleNameProvider {
    fun getModuleNames(): List<String>
}