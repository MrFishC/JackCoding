package com.jack.aidl01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 13:12
 * @描述
 */
public class GetTwoNumbersService extends Service {

    private int mReuslt;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mInterface;
    }

    private final AidlDemoInterface.Stub mInterface = new AidlDemoInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void sendTwoNums(int nums1, int nums2) throws RemoteException {
            mReuslt = nums1 + nums2;
            System.out.println(" 查看数据 === " + nums1);
            System.out.println(" 查看数据 === " + nums2);
        }

        @Override
        public int getTwoNumsResult() throws RemoteException {
            return mReuslt;
        }
    };

}
