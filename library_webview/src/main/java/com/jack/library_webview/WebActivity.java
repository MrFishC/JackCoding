package com.jack.library_webview;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jack.library_command.command.base.CommandLisenter;
import com.jack.library_command.command.callback.CommandCallBack;
import com.jack.library_command.command.constants.CommandConstants;
import com.jack.library_command.command.manager.CommandsManager;
import com.jack.library_webview.databinding.ActivityCommonWebBinding;
import com.jack.library_webview.fragment.BaseH5Fragment;
import com.limpoxe.support.library_service_manager.ServiceManager;

import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_common_business.service.ServiceConstants;
import cn.jack.library_common_business.service.baseservice.ILoginService;
import jack.wrapper.base.mvvm.view.activity.BaseSimpleActiviy;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 22:51
 * @描述
 */
@Route(path = RouterPathActivity.Web.PAGER_WEB)
public class WebActivity extends BaseSimpleActiviy<ActivityCommonWebBinding> {

    @Autowired
    String webUrl;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_common_web;
    }

    @Override
    public boolean injectARouter() {
        return true;
    }

    @Override
    public void prepareParam() {
        super.prepareParam();

//        registerCommandInfo();
//        registerCommandInfo2();
//        registerCommandInfo3();
//        registerCommandInfo4();
//        registerCommandInfo5();
//        registerCommandInfo6();
//        registerCommandInfo7();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.web_view_fragment, BaseH5Fragment.newInstance(webUrl)).commit();
    }

    /**
     * 需要做什么事情，直接注册对应的command即可
     */
    private void registerCommandInfo() {
        //需要执行的逻辑可以定义在匿名内部类中
        CommandsManager.getInstance().registerCommand(CommandConstants.LEVEL_LOCAL, new CommandLisenter() {
            @Override
            public String commandId() {
                return WebConstants.C_COMMAND.C_COMMAND_0X001;
            }

            @Override
            public void exec(Context context, CommandCallBack commandCallBack) {
                System.out.println(" 数据流向 ===> 111 ");
                commandCallBack.onResult(0,"11111",new Object());       //将结果回传回去
            }
        });
    }

    private void registerCommandInfo2() {
        //需要执行的逻辑可以定义在匿名内部类中
        CommandsManager.getInstance().registerCommand(CommandConstants.LEVEL_LOCAL, new CommandLisenter() {
            @Override
            public String commandId() {
                return WebConstants.C_COMMAND.C_COMMAND_0X002;
            }

            @Override
            public void exec(Context context, CommandCallBack commandCallBack) {
                System.out.println(" 数据流向 ===> 222 ");
            }
        });
    }

    private void registerCommandInfo3() {
        //需要执行的逻辑可以定义在匿名内部类中
        CommandsManager.getInstance().registerCommand(CommandConstants.LEVEL_LOCAL, new CommandLisenter() {
            @Override
            public String commandId() {
                return WebConstants.C_COMMAND.C_COMMAND_0X003;
            }

            @Override
            public void exec(Context context, CommandCallBack commandCallBack) {
                System.out.println(" 数据流向 ===> 333 ");
            }
        });
    }

    private void registerCommandInfo4() {
        //需要执行的逻辑可以定义在匿名内部类中
        CommandsManager.getInstance().registerCommand(CommandConstants.LEVEL_LOCAL, new CommandLisenter() {
            @Override
            public String commandId() {
                return WebConstants.C_COMMAND.C_COMMAND_0X004;
            }

            @Override
            public void exec(Context context, CommandCallBack commandCallBack) {
                System.out.println(" 数据流向 ===> 444 ");
            }
        });
    }

    private void registerCommandInfo5() {
        //需要执行的逻辑可以定义在匿名内部类中
        CommandsManager.getInstance().registerCommand(CommandConstants.LEVEL_LOCAL, new CommandLisenter() {
            @Override
            public String commandId() {
                return WebConstants.C_COMMAND.C_COMMAND_0X005;
            }

            @Override
            public void exec(Context context, CommandCallBack commandCallBack) {

                //commandCallBack ： 根据回调过来的数据 执行对应的逻辑

                System.out.println(" 数据流向 ===> 555 ");
                ILoginService iLoginService = (ILoginService) ServiceManager.getService(ServiceConstants.C_LOGIN);
                //根据H5页面传递过来的参数来判断是否登录成功
                if(!iLoginService.isLogin()){   //暂时先写定，未登录
                    iLoginService.login("","");
                }
            }
        });
    }

    private void registerCommandInfo6() {
        //需要执行的逻辑可以定义在匿名内部类中
        CommandsManager.getInstance().registerCommand(CommandConstants.LEVEL_LOCAL, new CommandLisenter() {
            @Override
            public String commandId() {
                return WebConstants.C_COMMAND.C_COMMAND_0X006;
            }

            @Override
            public void exec(Context context, CommandCallBack commandCallBack) {
                System.out.println(" 数据流向 ===> 666 ");
            }
        });
    }

    private void registerCommandInfo7() {
        //需要执行的逻辑可以定义在匿名内部类中
        CommandsManager.getInstance().registerCommand(CommandConstants.LEVEL_LOCAL, new CommandLisenter() {
            @Override
            public String commandId() {
                return WebConstants.C_COMMAND.C_COMMAND_0X007;
            }

            @Override
            public void exec(Context context, CommandCallBack commandCallBack) {
                System.out.println(" 数据流向 ===> 777 ");

                //展示原生的对话框 todo

            }
        });
    }

}
