package cn.jack.library_weight.flow

import android.view.View

/**
 * @创建者 Jack
 * @创建时间 2022/11/23 11:49
 * @描述
 */
abstract class FlowAdapter(itemInfo: List<String>) {

    var mItemInfos = itemInfo

    abstract fun getView(flowLayout: UFlowLayout, mark: String): View

    fun getItemCounts(): Int {
        return mItemInfos.size ?: 0
    }

    fun getItem(position: Int): String {
        return mItemInfos[position]
    }
}