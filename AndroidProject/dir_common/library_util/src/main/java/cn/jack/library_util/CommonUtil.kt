package cn.jack.library_util

import android.graphics.Color
import java.util.*

/**
 * Created by chenxz on 2018/5/14.
 */
object CommonUtil {

    /**
     * 获取随机rgb颜色值
     */
    fun randomColor(): Int {
        val random = Random()
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        var red = random.nextInt(190)
        var green = random.nextInt(190)
        var blue = random.nextInt(190)
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue)
    }
}