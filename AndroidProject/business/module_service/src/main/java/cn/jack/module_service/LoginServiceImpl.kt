//package cn.jack.module_service
//
//import android.content.Context
//import com.alibaba.android.arouter.facade.annotation.Route
//
///**
// * @创建者 Jack
// * @创建时间 2024-06-10 16:38
// * @描述 实现接口
// *
// * 另外一端发现服务的方式，推荐使用发现服务，或者封装一个单例对象
// *
// * 问题：Arouter并未产生 对应的LoginServiceImpl文件，具体原因不明，待排查  猜测：路由分组重复（问题之一，但是不止这个问题）
// * 把报错复现 记录一下
// * 记录分析的思路
// *
// * 将同样的代码迁移到 basicArouter 模块下，可以正常运行，经过检测，未发现该模块的配置方面有什么不一样，待后续再排查
// */
//@Route(path = LoginServiceImplPath.PATH)
//class LoginServiceImpl : ILoginService {
//    override fun login(context: Context?) {
//        //跳转到登录页面，相关的清除逻辑
//        println("LoginServiceImpl ")
////        ARouter.getInstance().build(RouterPathActivity.Login.PAGER_LOGIN)
////            .navigation()
//    }
//
//    override fun isLogin(): Boolean {
//        //根据逻辑来判断是否登录
//        return false  //测试
//    }
//
//    override fun init(context: Context?) {
//
//    }
//}
//
//object LoginServiceImplPath {
//    const val PATH = "/testl/LoginServiceImpl"
//}