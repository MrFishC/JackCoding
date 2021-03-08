package cn.jackmvvm.mvvm.model.repository;

import jack.wrapper.base.mvvm.model.BaseModel;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-17
 * describe:登录页面m层具体的数据操作类
 *
 * 一般需要将本地和网络数据的操作分离开来，而登录页面的数据操作仅仅只有一个请求登录的接口，没必要额外新增对本地数据操作的接口
 *
 * ILoginHttpDataSource:m层 与 vm层 的桥梁
 *
 */
public class LoginRepository extends BaseModel implements ILoginHttpDataSource<ILoginLisenter>{

    @Override
    public void login(String userName, String passwd, final ILoginLisenter lisenter) {

        //经过联网操作获取到了数据          下方是代码不能执行成功 URL不对

        //跳转主页面
        lisenter.openMainActivtiy();

//        RxBaseSubscriber<String> subscriber = new RxBaseSubscriber<String>() {
//
//            @Override
//            public void onError(APIException e) {
//                //接口请求失败哦
//
//            }
//
//            @Override
//            public void onSuccess(String str) {
//                //接口请求成功
//
//            }
//
//        };
//
//        //处理rxjava的内存泄露
//        addSubscribe(subscriber);
//
//        RxUtils
//                .getInstance()
//                .obtainRetrofitService(APIService.class)
//                .getInfos()
//                .map(new RxFunction<String>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);

    }

    @Override
    public void getHttpData(ILoginLisenter iBridge) {

    }

}
