// IBinderPool.aidl
package cn.jack.library_webview;

// Declare any non-default types here with import statements

interface IBinderPool {

    //查找特定Binder的方法
    IBinder queryBinder(int binderCode);

}