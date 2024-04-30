package cn.jack.library_common_business.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import cn.jack.library_common_business.R
import cn.jack.library_common_business.constant.C
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.library_image.util.ImageU.Companion.loadByNet
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 文章列表
 */
class ArticleInfoAdapter : BaseMultiItemQuickAdapter<ArticleInfo, BaseViewHolder>(null) {
    init {
        addItemType(C.ARTICLE_ITEM_TYPE_01, R.layout.layout_articke_type_01_item)
        addItemType(C.ARTICLE_ITEM_TYPE_02, R.layout.layout_articke_type_02_item)
        addChildClickViewIds(R.id.image_collection)
    }

//    private val diffCallBack = object : DiffUtil.ItemCallback<ArticleInfo>() {
//        override fun areItemsTheSame(oldItem: ArticleInfo, newItem: ArticleInfo): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: ArticleInfo, newItem: ArticleInfo): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    private val differ = AsyncListDiffer(this, diffCallBack)
//
//    var dataList: List<ArticleInfo>
//        get() = differ.currentList
//        set(value) {
//            differ.submitList(value)
//        }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }

    override fun convert(holder: BaseViewHolder, item: ArticleInfo) {
        when (getItemViewType(getItemPosition(item))) {
            C.ARTICLE_ITEM_TYPE_01 -> holder.apply {
                setText(R.id.tvTime, item.niceDate)
                setText(R.id.tvContent, item.title)
                setText(R.id.text_sort, "分类:" + item.chapterName)
                setGone(R.id.tvRefresh, !item.fresh)
                setImageResource(
                    R.id.image_collection,
                    if (item.collect) R.mipmap.icon_collect else R.mipmap.icon_uncollect
                )
            }

            C.ARTICLE_ITEM_TYPE_02 -> {

                holder.setText(
                    R.id.tvAuthor,
                    if (item.author == "") "" else "作者：" + item.author
                )
                    .setText(R.id.tvContent, item.desc)
                    .setImageResource(
                        R.id.image_collection,
                        if (item.collect) R.mipmap.icon_collect else R.mipmap.icon_uncollect
                    )
                loadByNet(holder.getView(R.id.image), item.envelopePic)
            }
        }
        holder.setText(R.id.tvTitle, item.title)
    }
}