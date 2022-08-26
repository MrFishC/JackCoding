package com.jack.lib_wrapper.ext

import android.app.Activity

/*通用的拓展函数可以单独写在一个lib中进行单独的管理*/
fun Activity.finishActivity() {
    finish()
}
