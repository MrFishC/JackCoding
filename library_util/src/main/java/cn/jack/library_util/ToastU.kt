package cn.jack.library_util

import android.app.Application
import android.widget.Toast
import es.dmoral.toasty.Toasty

/**
 * @创建者 Jack
 * @创建时间 2022/9/8 0008 11:28
 * @描述
 */
class ToastU private constructor() {

    companion object {
        private var context: Application? = null
        fun init(appContext: Application) {
            context = appContext

//            Toasty.Config.getInstance()
//                .tintIcon(boolean tintIcon) // optional (apply textColor also to the icon)
//                .setToastTypeface(@NonNull Typeface typeface) // optional
//                .setTextSize(int sizeInSp) // optional
//                .allowQueue(boolean allowQueue) // optional (prevents several Toastys from queuing)
//                .setGravity(boolean isRTL, int xOffset, int yOffset) // optional (set toast gravity, offsets are optional)
//                .supportDarkTheme(boolean isRTL) // optional (whether to support dark theme or not)
//                .setRTL(boolean isRTL) // optional (icon is on the right)
//                .apply(); // required
        }

        fun success(text: String, isShowIcon: Boolean = false) {
            if (context == null) throw IllegalStateException("Please call ToastU init function first.")
            Toasty.success(context!!, text, Toast.LENGTH_SHORT, isShowIcon).show();
        }

        fun error(text: String, isShowIcon: Boolean = false) {
            if (context == null) throw IllegalStateException("Please call ToastU init function first.")
            Toasty.error(context!!, text, Toast.LENGTH_SHORT, isShowIcon).show();
        }

        fun normal(text: String) {
            if (context == null) throw IllegalStateException("Please call ToastU init function first.")
            Toasty.normal(context!!, text, Toast.LENGTH_SHORT).show();
        }

        fun warn(text: String, isShowIcon: Boolean = false) {
            if (context == null) throw IllegalStateException("Please call ToastU init function first.")
            Toasty.warning(context!!, text, Toast.LENGTH_SHORT, isShowIcon).show();
        }

    }
}