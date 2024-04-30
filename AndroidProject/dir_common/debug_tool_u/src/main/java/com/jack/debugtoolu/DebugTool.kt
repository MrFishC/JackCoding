package com.jack.debugtoolu

import android.content.Context
import android.os.Build
import android.os.Process

/**
 * @创建者 Jack
 * @创建时间 2024-04-04 21:30
 * @描述 该模块方便在debug模式下进行调试
 *  参考，慕课网，移动架构师课程中的代码，仅作学习使用。若涉及到侵权，请告知，会及时删除。
 */
class DebugTool {
    fun buildVersion(): String {
        // 构建版本 ： v_1_1.0.1
        return "构建版本:v_" + BuildConfig.VERSION_CODE + "_" + BuildConfig.VERSION_NAME
    }

    fun buildEnvironment(): String {
        return "构建环境: " + if (BuildConfig.DEBUG) "测试环境" else "正式环境"
    }

    fun buildTime(): String {
        //new date() 当前你在运行时拿到的时间，这个包，被打出来的时间
        return "构建时间：" + BuildConfig.BUILD_APK_TIME
    }

    fun buildDevice(): String {
        // 构建版本 ： 品牌-sdk-abi
        return "设备信息:" + Build.BRAND + "-" + Build.VERSION.SDK_INT + "-" + Build.CPU_ABI
    }

    @HiDebug(name = "一键更换网络请求地址", desc = "开发环境和线上环境地址一键切换")
    fun switchRequestUrl(context: Context) {
        SPUtil.putBoolean(SPUtil.SWITCH_URL, true)
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        context.startActivity(intent)
        //杀掉当前进程,并主动启动新的 启动页，以完成重启的动作
        Process.killProcess(Process.myPid())
    }
}