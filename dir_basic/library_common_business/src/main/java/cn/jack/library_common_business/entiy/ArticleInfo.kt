package cn.jack.library_common_business.entiy

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import cn.jack.library_common_business.constant.C

data class ArticleInfo(
    var apkLink: String,
    var audit: Int,
    var author: String,
    var isCanEdit: Boolean,
    var chapterId: String,
    var chapterName: String,
    var isCollect: Boolean,
    var courseId: String,
    var desc: String,
    var descMd: String,
    var envelopePic: String,
    var isFresh: Boolean,
    var id: String,
    var link: String,
    var niceDate: String,
    var niceShareDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var realSuperChapterId: Int,
    var selfVisible: Int,
    var shareDate: Any,
    var shareUser: String,
    var superChapterId: Int,
    var superChapterName: String,
    var title: String,
    var userId: Int,
    var visible: Int,
    var zan: Int,
    var tags: List<*>
) : MultiItemEntity {
    override val itemType: Int
        get() = if (TextUtils.isEmpty(envelopePic)) {
            C.ARTICLE_ITEM_TYPE_01
        } else C.ARTICLE_ITEM_TYPE_02
}