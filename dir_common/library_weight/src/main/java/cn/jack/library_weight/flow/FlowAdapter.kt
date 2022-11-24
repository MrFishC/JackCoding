package cn.jack.library_weight.flow

import android.view.View

/**
 * @创建者 Jack
 * @创建时间 2022/11/23 11:49
 * @描述
 */
abstract class FlowAdapter<T>(datas: List<T>) {
    private val mTagDatas: List<T>?

    init {
        mTagDatas = datas
    }

    abstract fun getView(position: Int, t: T): View

    val count: Int
        get() = mTagDatas?.size ?: 0

    fun getItem(position: Int): T {
        return mTagDatas!![position]
    }
}