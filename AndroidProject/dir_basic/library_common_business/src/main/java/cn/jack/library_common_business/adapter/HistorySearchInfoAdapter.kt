package cn.jack.library_common_business.adapter

import cn.jack.library_common_business.R
import cn.jack.library_common_business.entiy.HistorySearchInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @创建者 Jack
 * @创建时间 2023/1/5 0005 18:30
 * @描述
 */
class HistorySearchInfoAdapter :
    BaseQuickAdapter<HistorySearchInfo, BaseViewHolder>(R.layout.layout_search_history_item) {
    init {
        addChildClickViewIds(R.id.iv_clear)
    }

    override fun convert(holder: BaseViewHolder, item: HistorySearchInfo) {
        holder.setText(R.id.tv_search_key, item.key)
    }
}