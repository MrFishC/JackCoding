package com.jack.lib_base.uistate.loadsir.callback;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.jack.lib_base.R;
import com.kingja.loadsir.callback.Callback;

/**
 * Description:TODO
 * Create Time:2017/9/2 16:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class NetErrorCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_net_error;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
//        Toast.makeText(context.getApplicationContext(),"Connecting to the network again!", Toast.LENGTH_SHORT).show();
        return false;
    }

}
