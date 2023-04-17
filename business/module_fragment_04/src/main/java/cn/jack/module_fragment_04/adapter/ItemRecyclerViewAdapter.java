package com.pj.module_main_first.mvvm.view.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pj.librarywrapper.utils.LoadLocalIconUtil;
import com.pj.librarywrapper.utils.UrlConfig;
import com.pj.librarywrapper.utils.image.ImageManager;
import com.pj.librarywrapper.utils.tools.LogUtils;
import com.pj.module_main_first.R;
import com.pj.module_main_first.mvvm.model.entiy.AllFunctionInfoRes;

/**
 * recycleview内部的recycleview
 */
public class ItemRecyclerViewAdapter extends BaseQuickAdapter<AllFunctionInfoRes.ChildrenBean, BaseViewHolder> {

    private int mWidth;
    private int mHeight;

    public ItemRecyclerViewAdapter(int layoutResId, int width, int height) {
        super(layoutResId);
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override
    protected void convert(BaseViewHolder helper, AllFunctionInfoRes.ChildrenBean item) {

        AllFunctionInfoRes.ChildrenBean.AttributesBean attributesBean = item.getAttributes();

//        if(attributesBean != null){
        helper.setText(R.id.item_common_funcation_content_name, attributesBean.getAppFunctionName());

        //判断是原生还是h5
//            if (attributesBean.getInvokingWay() == 1) {     //原生
//                if(!TextUtils.isEmpty(attributesBean.getAppFunctionIcon())){
//                    helper .setImageResource(R.id.item_common_funcation_content_icon, LoadLocalIconUtil.getInstance().drawablePathToInRes(attributesBean.getAppFunctionIcon()));
//                }
//            }else {
//                RequestOptions options = new RequestOptions()
//                        .error(R.drawable.icon_two)
//                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
//                Glide
//                        .with(mContext)
//                        .load(UrlConfig.getInstance().getOtherImgUrl() + attributesBean.getAppFunctionIcon())
//                        .apply(options)
//                        .into(((ImageView) helper.getView(R.id.item_common_funcation_content_icon)));
//            }
//        }else {
//            helper.setText(R.id.item_common_funcation_content_name,item.getName())
//                    .setImageResource(R.id.item_common_funcation_content_icon, R.drawable.icon_two);
//        }

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        RelativeLayout rootView = baseViewHolder.getView(R.id.inner_root_view);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mWidth, mHeight);
        rootView.setLayoutParams(layoutParams);
        return baseViewHolder;
    }

}


