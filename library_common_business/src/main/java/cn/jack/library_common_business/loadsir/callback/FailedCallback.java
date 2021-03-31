package cn.jack.library_common_business.loadsir.callback;

import com.kingja.loadsir.callback.Callback;
import cn.jack.library_common_business.R;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class FailedCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
