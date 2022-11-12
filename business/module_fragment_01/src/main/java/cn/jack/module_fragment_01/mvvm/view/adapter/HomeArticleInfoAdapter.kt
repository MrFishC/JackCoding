package cn.jack.module_fragment_01.mvvm.view.adapter

import android.text.TextUtils
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.module_fragment_01.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 16:43
 * @描述
 */
class HomeArticleInfoAdapter(layoutResId: Int) :
    BaseQuickAdapter<ArticleInfo, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, articleInfo: ArticleInfo) {
        helper
            .setGone(R.id.tag_fresh, articleInfo.isFresh)
            .setText(R.id.text_title, articleInfo.title)
            .setText(R.id.text_time, articleInfo.niceDate)
            .setText(
                R.id.text_content,
                if (TextUtils.isEmpty(articleInfo.desc)) articleInfo.descMd else articleInfo.desc
            )
            .setText(R.id.text_author, "作者:" + articleInfo.shareUser)
            .setText(R.id.text_sort, "分类:" + articleInfo.chapterName)
    }
}