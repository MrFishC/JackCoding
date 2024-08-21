package cn.jack.library_webview.command.coustom;

import android.content.Context;

import cn.jack.library_webview.command.CommandConstants;
import cn.jack.library_webview.command.base.Command;
import cn.jack.library_webview.command.base.CommandLisenter;
import cn.jack.library_webview.command.callback.CommandCallBack;
import cn.jack.library_webview.util.WebConstants;
import cn.jack.library_webview.command.callback.CommandCallBack;


/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:27
 * @描述
 */
public class LocalCommand extends Command {

    @Override
    protected int getCommandLevel() {
        return CommandConstants.LEVEL_LOCAL;
    }

    public LocalCommand() {
        super();
        registerCommand(toastCommandLisenter);
    }

    CommandLisenter toastCommandLisenter = new CommandLisenter() {
        @Override
        public String commandId() {
            return WebConstants.C_COMMAND.C_COMMAND_0X001;
        }

        @Override
        public void exec(Context context, CommandCallBack commandCallBack) {

            System.out.println("111111111111111111");
            commandCallBack.onResult(1,"12","123");
        }
    };

}
