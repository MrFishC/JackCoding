package cn.jack.lib_common

import cn.jack.lib_common.interceptor.TokenInterceptor
import cn.jack.library_image.util.ImageU
import cn.jack.library_util.*
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jack.lib_base.BuildConfig
import com.jack.lib_base.base.BaseApplication
import com.jack.lib_wrapper_net.manager.OkHttpManager
import com.jack.library_webview.cache.WebViewCacheU
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @创建者 Jack
 * @创建时间 2022/10/14 14:57
 * @描述  项目业务模块依赖层的Application，宿主层或独立的业务模块根据需要自定义实现类
 */
abstract class CustomApplication : BaseApplication() {
    override fun initOthers() {
        initNetwork()
        initImageLoader()
        initWebV()
        initMMKV()
        initLogger()
        initToastU()
        initContextU()
        initDisplayManager()
        initJsonU()
        initDensityU()
    }

    private fun initDensityU() {
        DensityU.init(this)
    }

    private fun initJsonU() {
        JsonU.init(this)
    }

    private fun initDisplayManager() {
        DisplayManager.init(this)
    }

    private fun initContextU() {
        ContextU.init(this, this)
    }

    private fun initToastU() {
        ToastU.init(this)
    }

    private fun initLogger() {
        LogU.init(isDebugMode(), "HiLog")
    }

    private fun initMMKV() {
        KvStoreUtil.getInstance().init(this)
    }

    private fun initWebV() {
        WebViewCacheU.init(this);
    }

    private fun initImageLoader() {
        ImageU.init()
    }

    private fun initNetwork() {
        val loggerInterceptor = HttpLoggingInterceptor { message ->
            LogU.d(message)
        }
        loggerInterceptor.level =
            if (isDebugMode()) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .addInterceptor(TokenInterceptor())
            .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this)))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        OkHttpManager.instance.initClient(builder.build())
    }

    protected fun isDebugMode() = BuildConfig.DEBUG
}