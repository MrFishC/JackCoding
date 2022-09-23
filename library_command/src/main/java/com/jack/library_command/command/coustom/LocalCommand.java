//package com.jack.library_command.command.coustom;
//
//import android.content.Context;
//import android.widget.Toast;
//
//import com.jack.library_command.command.base.CommandLisenter;
//import com.jack.library_command.command.callback.CommandCallBack;
//import com.jack.library_command.command.constants.CommandConstants;
//import com.jack.library_command.command.base.Command;
//import com.jack.library_command.command.constants.WebConstants;
//import com.jack.library_command.command.manager.CommandsManager;
//
///**
// * @创建者 Jack
// * @创建时间 2021/4/6 10:27
// * @描述
// */
//public class LocalCommand extends Command {
//
//    @Override
//    protected int getCommandLevel() {
//        return CommandConstants.LEVEL_LOCAL;
//    }
//
//    public LocalCommand() {
//        super();
//        registerCommand(toastCommandLisenter);
//    }
//
//    CommandLisenter toastCommandLisenter = new CommandLisenter() {
//        @Override
//        public String commandId() {
//            return WebConstants.C_COMMAND.C_COMMAND_0X001;
//        }
//
//        @Override
//        public void exec(Context context, CommandCallBack commandCallBack) {
//
//            System.out.println("111111111111111111");
//        }
//    };
//
//}
