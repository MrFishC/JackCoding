// IWeb2NativeCallback.aidl
package cn.jack.library_webview;

//主进程将结果回调给H5进程 所使用
interface IWeb2NativeCallback {
    void onResult(int responseCode, String actionName, String response);
}