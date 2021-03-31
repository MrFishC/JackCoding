package jack.wrapper.base.mvvm.view.interf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @创建者 Jack
 * @创建时间 2021/3/26 11:28
 * @描述  activity的普通跳转，可携带Bundle
 *  jdk1.8之后可以在接口中定义default方法
 */
public interface IOpenActivity {

    Context getContext();

    //default https://blog.csdn.net/wf13265/article/details/79363522

    default void openActivity(Class<? extends Activity> clz) {
        Intent intent = new Intent(getContext(), clz);
        openActivity(intent);
    }

    default void openActivity(Class<? extends Activity> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        openActivity(intent);
    }

    default void openActivity(Intent intent) {
        if (!(getContext() instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        getContext().startActivity(intent);
    }

}
