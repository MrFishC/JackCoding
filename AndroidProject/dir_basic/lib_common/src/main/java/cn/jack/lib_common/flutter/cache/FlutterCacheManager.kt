package cn.jack.lib_common.flutter.cache

import android.content.Context
import android.os.Looper
import cn.jack.lib_common.flutter.bridge.FlutterBridge
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterJNI
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader

/**
 * @创建者 Jack
 * @创建时间 2024-04-25 23:09
 * @描述
 */
class FlutterCacheManager private constructor() {

    companion object {

        @JvmStatic
        @get:Synchronized
        var instance: FlutterCacheManager? = null
            get() {
                if (field == null) {
                    field = FlutterCacheManager()
                }
                return field
            }
            private set
    }

    /**
     * 线程空闲时候执行预加载
     */
    fun preLoad(context: Context, moduleNameProvider: ModuleNameProvider) {
        Looper.myQueue().addIdleHandler {
            moduleNameProvider.getModuleNames().forEach { moduleName ->
                initFlutterEngine(context, moduleName)
            }
            false
        }
    }

    /**
     * 初始化Flutter
     */
    private fun initFlutterEngine(context: Context, moduleName: String): FlutterEngine {
        //flutter 引擎
        val flutterLoader: FlutterLoader = FlutterInjector.instance().flutterLoader()
        val flutterEngine = FlutterEngine(context, flutterLoader, FlutterJNI())

        //初始化flutter与native通信的插件
        FlutterBridge.init(flutterEngine)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint(
                flutterLoader.findAppBundlePath(),
                moduleName
            )
        )
        //使用缓存
        FlutterEngineCache.getInstance().put(moduleName, flutterEngine)
        return flutterEngine
    }

    /**
     * 通过缓存获取
     */
    fun getCachedFlutterEngine(context: Context?, moduleName: String): FlutterEngine {
        var flutterEngine = FlutterEngineCache.getInstance()[moduleName]
        if (flutterEngine == null && context != null) {
            flutterEngine = initFlutterEngine(context, moduleName)
        }
        return flutterEngine!!
    }

    /**
     * 销毁FlutterEngine
     */
    fun destroyCached(moduleName: String) {
        FlutterEngineCache.getInstance()[moduleName]?.apply {
            destroy()
        }
        FlutterEngineCache.getInstance().remove(moduleName)
    }

}