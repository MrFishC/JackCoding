package jack.wrapper.base.contract;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-8-31
 * describe:基类契约类,子类只需要关注IBridge接口即可;
 * 子类通过实现接口IBridge,拓展自定义方法;
 */
public interface IBaseContract {

    /**
     * 注意：
     * 该接口出现的位置有两处,Repository和ViewModel;
     * 使用时候均需要带上泛型IB
     * @param <IB> IBridge的子类
     */
    interface IHttpDataSource<IB extends IBridge> {
        //获取数据/执行网络操作
        void getHttpData(IB iBridge);
    }

    /**
     * 该接口出现的位置有两处,Repository和ViewModel;
     * 使用时候均需要带上泛型IB
     * @param <IB> IBridge的子类
     */
    interface ILocalDataSource<IB extends IBridge>{
        //进行本地数据数据
        void getLocalData(IB iBridge);
    }

    /**
     * 该接口或子类实现的方法,会在对应的vm层(ViewModel)中得到回调
     */
    interface IBridge {

        //这里规定一些通用的方法,辅助于状态布局
        //开始加载
        //完成加载

        //子类自行规定加载网络或本地数据的方法

    }

}
