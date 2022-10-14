package cn.jack.lib_common.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.jack.library_util.ToastU

/**
 * @创建者 Jack
 * @创建时间 2022/10/14
 * @描述
 */

fun Fragment.showToast(text: String?) {
    text?.let {
        ToastU.normal(it)
    }
}

fun AppCompatActivity.showToast(text: String?) {
    text?.let {
        ToastU.normal(it)
    }
}