package cn.jack.library_common_business.loadsir.callback;

import android.content.Context;
import android.view.View;
import com.kingja.loadsir.callback.Callback;
import cn.jack.library_common_business.R;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }


    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
