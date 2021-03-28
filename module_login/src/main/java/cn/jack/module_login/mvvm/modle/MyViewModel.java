package cn.jack.module_login.mvvm.modle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 18:22
 * @描述
 */
public class MyViewModel extends ViewModel {

    private MutableLiveData<Integer> mutableLiveData;

    public MutableLiveData<Integer> getMutableLiveData() {
        if (mutableLiveData==null) {
            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.setValue(0);
        }
        return mutableLiveData;
    }

    public void addCount(){
        System.out.println(" mutableLiveData.getValue() " + mutableLiveData.getValue());
        mutableLiveData.setValue(mutableLiveData.getValue()+1);
    }
}
