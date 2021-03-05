package jack.wrapper.base.contract.base;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-8-31
 * describe:
 *  基类契约类
 *      定义网络请求规定的接口
 *      定义本地数据查询规定的接口
 *
 *      该接口可以不需要对外进行暴露，只提供给本包的类使用，保留public，则保留其拓展性;
 */
public interface IContract {

    /**
     * 注意：
     * 该接口出现的位置有两处,Repository和ViewModel;
     * 使用时候均需要带上泛型IB
     * @param <IB> IBridge的子类
     */
    interface IHttpSource<IB extends IBridge> {

    }

    /**
     * 该接口出现的位置有两处,Repository和ViewModel;
     * 使用时候均需要带上泛型IB
     * @param <IB> IBridge的子类
     */
    interface ILocalSource<IB extends IBridge>{

    }

    /**
     * 该接口或子类实现的方法,会在对应的vm层(ViewModel)中得到回调
     */
    interface IBridge {

        //子类自行规定加载网络或本地数据的方法

    }

}
