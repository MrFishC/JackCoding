package com.jack.library_command.command.callback;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:23
 * @描述
 */
public interface CommandCallBack {
    void onResult(int status, String action, Object result);
}
