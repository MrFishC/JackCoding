package com.jack.library_webview.command.base;

import java.util.HashMap;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:26
 * @描述
 *      命令类型的基类
 *      具体的业务逻辑在其实现类中实现即可
 */
public abstract class Command {
    private HashMap<String, CommandLisenter> mCommandLisenterHashMap;

    protected abstract int getCommandLevel();

    public HashMap<String, CommandLisenter> getCommandLisenterHashMap() {
        return mCommandLisenterHashMap;
    }

    public Command() {
        mCommandLisenterHashMap = new HashMap<>();
    }

    public void registerCommand(CommandLisenter commandLisenter) {
        System.out.println("registerCommand 1 commandLisenter.commandId() = " + commandLisenter.commandId());
        mCommandLisenterHashMap.put(commandLisenter.commandId(), commandLisenter);
        System.out.println("registerCommand 2 mCommandLisenterHashMap.size() =" + mCommandLisenterHashMap.size());
    }
}
