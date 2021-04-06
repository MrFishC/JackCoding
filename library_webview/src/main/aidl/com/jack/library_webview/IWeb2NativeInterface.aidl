// IWeb2NativeInterface.aidl
package com.jack.library_webview;

// Declare any non-default types here with import statements
import com.jack.library_webview.IWeb2NativeCallback;

//h5进程 调用主进程 所使用
interface IWeb2NativeInterface {
    //根据实际需求定义参数
    void handleWebAction(int level, String actionName, in IWeb2NativeCallback callback);
}