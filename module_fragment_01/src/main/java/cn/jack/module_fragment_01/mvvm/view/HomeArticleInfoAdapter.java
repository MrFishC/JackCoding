package cn.jack.module_fragment_01.mvvm.view;

import android.text.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import org.jetbrains.annotations.NotNull;
import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.module_fragment_01.R;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 16:43
 * @描述
 */
public class HomeArticleInfoAdapter extends BaseQuickAdapter<ArticleInfo, BaseViewHolder> {

    public HomeArticleInfoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ArticleInfo articleInfo) {
        helper
                .setGone(R.id.tag_fresh,articleInfo.isFresh())
                .setText(R.id.text_title,articleInfo.getTitle())
                .setText(R.id.text_time,articleInfo.getNiceDate())
                .setText(R.id.text_content, TextUtils.isEmpty(articleInfo.getDesc()) ? articleInfo.getDescMd() : articleInfo.getDesc())
                .setText(R.id.text_author,"作者:" + articleInfo.getShareUser())
                .setText(R.id.text_sort,"分类:" + articleInfo.getChapterName())
                ;
    }

}
