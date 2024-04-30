package com.jack.library_webview.command.coustom;


import com.jack.library_webview.command.CommandConstants;
import com.jack.library_webview.command.base.Command;

public class LoginCommand extends Command {

    public LoginCommand() {
    }

    @Override
    protected int getCommandLevel() {
        return CommandConstants.LEVEL_LOGIN;
    }
}
