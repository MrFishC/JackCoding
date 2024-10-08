package cn.jack.library_webview.command.base;

import android.content.Context;

import cn.jack.library_webview.command.callback.CommandCallBack;
import cn.jack.library_webview.command.callback.CommandCallBack;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:30
 * @描述
 */
public interface CommandLisenter {
    String commandId();     //需要定义成唯一的标识
    void exec(Context context, CommandCallBack commandCallBack);
}
