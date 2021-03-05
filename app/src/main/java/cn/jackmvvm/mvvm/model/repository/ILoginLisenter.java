package cn.jackmvvm.mvvm.model.repository;

import jack.wrapper.base.contract.IBaseContract;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-17
 * describe:LoginViewModel与LoginRepository的桥梁
 *
 * [另一种方案]将m层逻辑转移到vm层也是非常不错的，避免了更多的接口;
 * [项目使用的方案]新建该接口可以使结构更清晰，代价是增加了系统的负责度;
 */
public interface ILoginLisenter extends IBaseContract.IBridge{

    //显示对话框
    void showLoadDialog();

    //关闭对话框
    void closeLoadDialog();

    //跳转到主页面
    void openMainActivtiy();

}
