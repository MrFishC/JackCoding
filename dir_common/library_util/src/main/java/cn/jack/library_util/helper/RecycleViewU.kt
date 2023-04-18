package cn.jack.library_util.helper

import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Field

/**
 * @创建者 Jack
 * @创建时间 2023/4/18 0018 11:13
 * @描述 recyclerView滑动过快的时候会出现bug,限制recyclerView的最大滑动速度可以处理该问题
 */
object RecycleViewU {
    fun setMaxFlingVelocity(recyclerView: RecyclerView, velocity: Int) {
        try {
            val field: Field = recyclerView.javaClass.getDeclaredField("mMaxFlingVelocity")
            field.isAccessible = true
            field.set(recyclerView, velocity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}