package cn.jack.library_common_business.adapter;

import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import cn.jack.library_common_business.R;
import cn.jack.library_common_business.constant.C;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_image.util.ImageU;

/**
 * 文章列表
 */
public class ArticleInfoAdapter extends BaseMultiItemQuickAdapter<ArticleInfo, BaseViewHolder> {

    public ArticleInfoAdapter() {
        super(null);
        addItemType(C.ARTICLE_ITEM_TYPE_01, R.layout.layout_articke_type_01_item);
        addItemType(C.ARTICLE_ITEM_TYPE_02, R.layout.layout_articke_type_02_item);
        addChildClickViewIds(R.id.image_collection);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ArticleInfo articleBean) {
        String superChapterName = articleBean.getSuperChapterName();
        String chapterName = articleBean.getChapterName();

        switch (getItemViewType(getItemPosition(articleBean))) {
            case C.ARTICLE_ITEM_TYPE_01:
                helper
                        .setText(R.id.tvChapter,
                                TextUtils.isEmpty(superChapterName) ? chapterName : String.format("%s·%s", superChapterName, chapterName))
                        .setText(R.id.tvTime, articleBean.getNiceDate())
                        .setGone(R.id.tvRefresh, !articleBean.isFresh())
                        .setImageResource(R.id.image_collection,articleBean.isCollect() ? R.mipmap.icon_collect : R.mipmap.icon_uncollect);
                break;
            case C.ARTICLE_ITEM_TYPE_02:
                helper.setText(R.id.tvContent, articleBean.getDesc())
                        .setImageResource(R.id.image_collection,articleBean.isCollect() ? R.mipmap.icon_collect : R.mipmap.icon_uncollect);
                ImageU.Companion.loadByNet(((AppCompatImageView) helper.getView(R.id.image)),articleBean.getEnvelopePic());
//                ImageManager.Companion.getInstance().getImageLoader().loadImageByNet(helper.getView(R.id.image).getContext(),articleBean.getEnvelopePic(), ((AppCompatImageView) helper.getView(R.id.image)));
                break;
        }

        helper.setText(R.id.tvTitle, articleBean.getTitle())
                .setText(R.id.tvAuthor, TextUtils.isEmpty(articleBean.getAuthor()) ? articleBean.getShareUser() : articleBean.getAuthor());
    }

}
