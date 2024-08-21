package cn.jack.library_webview.command.coustom;


import cn.jack.library_webview.command.CommandConstants;
import cn.jack.library_webview.command.base.Command;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:27
 * @描述
 */
public class RemoteCommand extends Command {

    @Override
    protected int getCommandLevel() {
        return CommandConstants.LEVEL_REMOTE;
    }

    public RemoteCommand() {
        super();
    }

}
