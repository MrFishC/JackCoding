package cn.jack.library_util.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.jack.library_util.ToastU
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

//    if (dialog == null) {
//        dialog = MaterialDialog(this).show {
//            customView(    //自定义弹窗
//                viewRes = R.layout.custom_loading_dialog,//自定义文件
////            dialogWrapContent = true,    //让自定义宽度生效
////            scrollable = true,            //让自定义宽高生效
//                noVerticalPadding = true    //让自定义高度生效
//            )
//            lifecycleOwner(this@loadDialog)//绑定生命周期
//            cancelOnTouchOutside(false)    //点击外部不消失
//        }
//    }
//
//    dialog!!.onShow {
//        it.getCustomView().findViewById<TextView>(R.id.title).text = title
//        it.getCustomView().findViewById<TextView>(R.id.content).text = title
//    }
//
//    return dialog as MaterialDialog

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

