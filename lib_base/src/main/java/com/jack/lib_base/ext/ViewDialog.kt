package com.jack.lib_base.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

/**
 * @创建者 Jack
 * @创建时间 2022/9/8 0008 12:36
 * @描述
 */

var popupView: BasePopupView? = null

//显示对话框
fun AppCompatActivity.loadDialog(title: String = "正在加载中..."): BasePopupView {
    if (popupView == null) {
        popupView = XPopup.Builder(this)
            .asLoading(title)
            .show()
    }
    return popupView!!
}


//关闭对话框
fun AppCompatActivity.closeDialog(delay: Long = 300) {
    popupView?.delayDismiss(delay)
}

fun Fragment.loadDialog(title: String = "正在加载中..."): BasePopupView {
    if (popupView == null) {
        popupView = XPopup.Builder(this.context)
            .asLoading(title)
            .show()
    }

    return popupView!!
}


fun Fragment.closeDialog(delay: Long = 300) {
    popupView?.delayDismiss(delay)
}


