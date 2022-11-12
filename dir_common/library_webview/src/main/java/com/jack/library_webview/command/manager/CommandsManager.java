package com.jack.library_webview.command.manager;

import android.content.Context;

import com.jack.library_webview.command.CommandConstants;
import com.jack.library_webview.command.base.CommandLisenter;
import com.jack.library_webview.command.callback.CommandCallBack;
import com.jack.library_webview.command.coustom.LocalCommand;
import com.jack.library_webview.command.coustom.LoginCommand;
import com.jack.library_webview.command.coustom.RemoteCommand;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 9:54
 * @描述 Command管理者，所有的组件可以公用。
 */
public class CommandsManager {

    //=====================================单例===========================================
    public static CommandsManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final CommandsManager INSTANCE = new CommandsManager();
    }

    private LocalCommand  mLocalCommand;
    private RemoteCommand mRemoteCommand;
    private LoginCommand  mLoginCommand;

    private CommandsManager() {
        mRemoteCommand = new RemoteCommand();
        mLocalCommand = new LocalCommand();
        mLoginCommand = new LoginCommand();
    }

    /**
     * 存在问题
     * 通过该方法注册的CommandLisenter,根据对应的Command实现类去获取mCommandLisenterHashMap集合会存在问题
     * (execCommand方法在其它进程被调用之后,获取对应Command实现类的mCommandLisenterHashMap集合 内容不同)
     */
//    public void registerCommand(int commandLevel, CommandLisenter commandLisenter) {
//        System.out.println(" registerCommand ===>>>>>>>>>>>> ");
//        switch (commandLevel) {
//            case CommandConstants.LEVEL_LOCAL:
//                System.out.println(" registerCommand LEVEL_LOCAL");
//                mLocalCommand.registerCommand(commandLisenter);
//                break;
//            case CommandConstants.LEVEL_REMOTE:
//                System.out.println(" registerCommand LEVEL_REMOTE");
//                mRemoteCommand.registerCommand(commandLisenter);
//                break;
//            case CommandConstants.LEVEL_LOGIN:
//                System.out.println(" registerCommand LEVEL_LOGIN");
//                mLoginCommand.registerCommand(commandLisenter);
//                break;
//            //other - coustom
//        }
//
//        System.out.println(" registerCommand  mCommandLisenterHashMap---size11 = " + mLocalCommand.getCommandLisenterHashMap().size());
//        System.out.println(" registerCommand  mCommandLisenterHashMap---size22 = " + mRemoteCommand.getCommandLisenterHashMap().size());
//        System.out.println(" registerCommand  mCommandLisenterHashMap---size33 = " + mLoginCommand.getCommandLisenterHashMap().size());
//
//        int pid = android.os.Process.myPid();
//        System.out.println("registerCommand pid = " + pid);
//    }

    //=====================================调用command方法=====================================
    public void execCommand(Context context, int level,String cmd, String param, CommandCallBack commandCallBack) {
        //在哪个进程注册的,就需要在哪个进程中获取
        System.out.println(" execCommand >>> level " + level + " cmd " + cmd + " param " + param);
        System.out.println(" execCommand  mCommandLisenterHashMap---size1 = " + mLocalCommand.getCommandLisenterHashMap().size());
        System.out.println(" execCommand  mCommandLisenterHashMap---size2 = " + mRemoteCommand.getCommandLisenterHashMap().size());
        System.out.println(" execCommand  mCommandLisenterHashMap---size3 = " + mLoginCommand.getCommandLisenterHashMap().size());
        switch (level){
            case CommandConstants.LEVEL_LOCAL:
                CommandLisenter commandLisenter = mLocalCommand.getCommandLisenterHashMap().get(cmd);
                int pid = android.os.Process.myPid();
                System.out.println("execCommand pid = " + pid);
                System.out.println(" execCommand 16 commandLisenter == null " + (commandLisenter == null));
                if (commandLisenter != null) {
                    System.out.println(" execCommand 17");
                    commandLisenter.exec(context, commandCallBack);
                }
                break;
            case CommandConstants.LEVEL_REMOTE:
                CommandLisenter remoteLisenter = mRemoteCommand.getCommandLisenterHashMap().get(cmd);
                System.out.println(" execCommand 3");
                if (remoteLisenter != null) {
                    System.out.println(" execCommand 4");
                    remoteLisenter.exec(context, commandCallBack);
                }
                break;
            case CommandConstants.LEVEL_LOGIN:
                System.out.println(" execCommand 5");
                CommandLisenter loginLisenter = mLoginCommand.getCommandLisenterHashMap().get(cmd);
                if (loginLisenter != null) {
                    System.out.println(" execCommand 6");
                    loginLisenter.exec(context, commandCallBack);
                }
                break;
        }
    }
}
