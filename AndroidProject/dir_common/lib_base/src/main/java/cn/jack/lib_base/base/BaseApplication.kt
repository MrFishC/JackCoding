package cn.jack.lib_base.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import android.webkit.WebView
import androidx.multidex.MultiDex
import cn.jack.debugtoolu.DebugManager
import cn.jack.lib_base.uistate.loadsir.callback.*
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 17:05
 * @描述
 */
abstract class BaseApplication : Application() {
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
            } else {
                //处理：Android P 不支持同时使用多个进程中具有相同数据目录的WebView
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    WebView.setDataDirectorySuffix(processName)
                }
            }
        }
    }

    /*子类重写该方法可以执行额外的初始化工作,若没有额外的初始化工作,直接在AndroidManiest.xml中配置该application;*/
    private fun init() {
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
        initDebugTool()
        initLoadSir()
//        initBus()
        initOthers()
        /*注册监听每个activity的生命周期,便于堆栈式管理*/

    }

    private fun initDebugTool() {
        DebugManager.init(this)
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())
            .addCallback(FailedCallback())
            .addCallback(EmptyCallback())
            .addCallback(TimeoutCallback())
            .addCallback(NetErrorCallback())
            .addCallback(CustomCallback())
            //注意：因为使用了Flow，封装了onCompletion回调，故在使用状态布局的时候，设置SuccessCallback的位置需要注意
            //要结合flow的封装情况来设置
            .setDefaultCallback(SuccessCallback::class.java)    //这里可以不设置
            .commit()
    }

    abstract fun initOthers()
    abstract fun initArouter()
}