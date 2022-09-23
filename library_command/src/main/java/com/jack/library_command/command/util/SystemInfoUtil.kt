//package com.jack.library_command.command.util
//
//import android.app.ActivityManager
//import android.content.Context
//
///**
// * @创建者 Jack
// * @创建时间 2022/9/21 18:04
// * @描述
// */
//class SystemInfoUtil {
//    companion object {
//        /**
//         * 判断当前是否是主进程
//         */
//        fun inMainProcess(context: Context, pid: Int): Boolean {
//            val packageName = context.packageName
//            val processName = getProcessName(context, pid)
//            return packageName == processName
//        }
//
//        /**
//         * 获取当前进程名
//         * @param context
//         * @return 进程名
//         */
//        fun getProcessName(context: Context, pid: Int): String? {
//            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//            val runningApps = am.runningAppProcesses ?: return null
//            for (procInfo in runningApps) {
//                if (procInfo.pid == pid) {
//                    return procInfo.processName
//                }
//            }
//            return null
//        }
//    }
//}