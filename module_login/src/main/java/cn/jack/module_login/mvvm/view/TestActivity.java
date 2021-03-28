package cn.jack.module_login.mvvm.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivityTestBinding;
import cn.jack.module_login.mvvm.modle.MyViewModel;

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 18:26
 * @描述 databinding跟livedata关联的小例子
 *
 * 例子出来了，研究一下其中的原理
 */
public class TestActivity extends AppCompatActivity {
    MyViewModel         myViewModel;
    ActivityTestBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        activityMainBinding.setData(myViewModel);
        activityMainBinding.setLifecycleOwner(this);      //该行代码使用的情况下，不能更新UI

//        myViewModel.getMutableLiveData().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                System.out.println("onChanged " + integer);
//            }
//        });

    }
}
