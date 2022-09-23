//package com.jack.library_command.command.dispatcher;
//
//import android.content.Context;
//import com.jack.library_command.command.callback.CommandCallBack;
//import com.jack.library_command.command.manager.CommandsManager;
//
///**
// * @创建者 Jack
// * @创建时间 2021/4/6 9:57
// * @描述 初始化aidl，管理native跟h5之间交互的事件
// */
//public class CommonDispatcher {
//    private static CommonDispatcher instance;
//
//    private CommonDispatcher() {
//
//    }
//
//    public static CommonDispatcher getInstance() {
//        if (instance == null) {
//            synchronized (CommonDispatcher.class) {
//                if (instance == null) {
//                    instance = new CommonDispatcher();
//                }
//            }
//        }
//        return instance;
//    }
//
//    /**
//     * 执行请求(这里只能在同一个进行操作)
//     */
////    public void execute(Context context, int commandLevel, String cmd,CommandCallBack commandCallBack){
////        CommandsManager.getInstance().execCommand(context, commandLevel, cmd, commandCallBack);
////    }
//
//
//}
