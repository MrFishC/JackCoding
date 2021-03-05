package jack.retrofit2_rxjava2.util.net;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import cn.jack.library_util.AppContext;


/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-7
 * describe:网络检测工具类
 */
public class NetCheckHelper {

    private ConnectivityManager mManager;

    private NetCheckHelper() {
        mManager = (ConnectivityManager) AppContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }
    /**
     * 单例
     */
    public static NetCheckHelper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final NetCheckHelper INSTANCE = new NetCheckHelper();
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public boolean isNetworkConnected() {

        if(mManager == null){
            mManager = (ConnectivityManager) AppContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        //新版本调用方法获取网络状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = mManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = mManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else {
            //否则调用旧版本方法
            if (mManager != null) {
                NetworkInfo[] info = mManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检测wifi是否连接
     *
     * @return
     */
    public boolean isWifiConnected() {
        if (mManager != null) {
            NetworkInfo networkInfo = mManager.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测3G是否连接
     *
     * @return
     */
    public boolean is3gConnected() {
        if (mManager != null) {
            NetworkInfo networkInfo = mManager.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测GPS是否打开
     * 待修改
     * @return
     */
    public static boolean isGpsEnabled(LocationManager lm) {
//        List<String> accessibleProviders = lm.getProviders(true);
//        for (String name : accessibleProviders) {
//            if ("gps".equals(name)) {
//                return true;
//            }
//        }
        return false;
    }
}
