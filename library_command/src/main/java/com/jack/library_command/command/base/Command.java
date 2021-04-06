package com.jack.library_command.command.base;

import java.util.HashMap;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:26
 * @描述
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
        mCommandLisenterHashMap.put(commandLisenter.commandId(), commandLisenter);
    }
}
