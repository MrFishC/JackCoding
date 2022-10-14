package com.jack.lib_base.ext
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager

/*通用的拓展函数可以单独写在一个lib中进行单独的管理*/
fun Activity.setStatusBarTranslucent(navigationBarColor: Int = Color.BLACK) {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        var flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 设置状态栏文字为深色
        }
        decorView.systemUiVisibility = flag
        statusBarColor = Color.TRANSPARENT
        this.navigationBarColor = navigationBarColor
    }
}

fun Activity.finisActivity() {
    finish()
}

//协变：将父类变为具体子类
fun Activity.launchActivity(clazz: Class<out Activity>, bundleExtras: Bundle? = null) {
    //apply函数的返回的是传入对象的本身
    //使用场景：1.一个对象在初始化的时候需要对对象的属性进行赋值;2.动态的加载出一个XML的View的时候需要给View绑定数据
    //代码会很简洁
    startActivity(Intent(this, clazz).apply {
        if (bundleExtras != null) putExtras(bundleExtras)
    })
}

