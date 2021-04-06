package com.jack.library_command.command.manager;

import android.content.Context;
import com.jack.library_command.command.constants.CommandConstants;
import com.jack.library_command.command.base.CommandLisenter;
import com.jack.library_command.command.callback.CommandCallBack;
import com.jack.library_command.command.coustom.LocalCommand;
import com.jack.library_command.command.coustom.LoginCommand;
import com.jack.library_command.command.coustom.RemoteCommand;

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
     * 动态注册command
     * 根据需求自定义command，然后在这里添加进来。
     *
     * todo 后续可以优化处理 command的动态添加。
     */
    public void registerCommand(int commandLevel, CommandLisenter commandLisenter) {
        System.out.println(" registerCommand " + commandLevel);
        switch (commandLevel) {
            case CommandConstants.LEVEL_LOCAL:
                mLocalCommand.registerCommand(commandLisenter);
                break;
            case CommandConstants.LEVEL_REMOTE:
                mRemoteCommand.registerCommand(commandLisenter);
                break;
            case CommandConstants.LEVEL_LOGIN:
                mLoginCommand.registerCommand(commandLisenter);
                break;
            //other - coustom
        }
    }

    //=====================================调用command方法=====================================
    public void execCommand(Context context, int commandLevel, String cmd, CommandCallBack commandCallBack) {
        switch (commandLevel){
            case CommandConstants.LEVEL_LOCAL:
                CommandLisenter commandLisenter = mLocalCommand.getCommandLisenterHashMap().get(cmd);
                System.out.println(" execCommand 1");
                if (commandLisenter != null) {
                    System.out.println(" execCommand 2");
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
