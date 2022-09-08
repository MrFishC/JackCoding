package com.jack.lib_base.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import androidx.multidex.MultiDex
import cn.jack.library_util.KvStoreUtil
import cn.jack.library_util.LogU
import cn.jack.library_util.ToastU
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jack.lib_base.BuildConfig
import jack.retrofit2_rxjava2.interceptor.TokenInterceptor
import com.jack.lib_wrapper_net.manager.OkHttpManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 17:05
 * @描述
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        checkApplication()
    }

    /*检查当前进程,确保application只初始化一次,提高性能
     具体请参考,https://blog.csdn.net/jason0539/article/details/45555671*/
    private fun checkApplication() {
        val processName: String? = getApplocationProcessName(this)
        if (processName != null) {
            if (processName == this.packageName) {
                init()
            }
        }
    }

    /*子类重写该方法可以执行额外的初始化工作,若没有额外的初始化工作,直接在AndroidManiest.xml中配置该application;*/
    protected fun init() {
        setApplication(this)
    }

    /*获取进程名称*/
    private fun getApplocationProcessName(context: Context): String? {
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (proInfo in runningApps) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName
                }
            }
        }
        return null
    }

    @Synchronized
    fun setApplication(application: Application) {
        initArouter()
        initNetwork()
//        initLoadSir()
//        initImageLoader()
//        initBus()
        initMMKV()
        initLogger()
        initToastU()
//        initServiceManager()

        /*注册监听每个activity的生命周期,便于堆栈式管理*/

    }

    private fun initToastU() {
        ToastU.init(this)
    }

    private fun initLogger() {
        LogU.init(BuildConfig.DEBUG,"HiLog")
    }

    private fun initMMKV() {
        KvStoreUtil.getInstance()?.init(this)
    }

    private fun initNetwork() {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor())
            .addInterceptor(logging)
            .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this)))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        OkHttpManager.instance.initClient(builder.build())
    }

    open fun initArouter(){

    }
}