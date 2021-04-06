package cn.jack.module_fragment_01.mvvm.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;
import java.util.List;
import cn.jack.library_image.image.ImageManager;
import cn.jack.module_fragment_01.R;
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos;

/**
 * 自定义布局，网络图片
 */
public class ImageNetAdapter extends BannerAdapter<BanInfos, ImageNetAdapter.ImageHolder> {

    public ImageNetAdapter(List<BanInfos> mDatas) {
        super(mDatas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        AppCompatImageView imageView = (AppCompatImageView) BannerUtils.getView(parent, R.layout.layout_banner_image);
        //通过裁剪实现圆角
        BannerUtils.setBannerRound(imageView, 20);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, BanInfos data, int position, int size) {
        ImageManager.getInstance().getImageLoader().loadImageByNet(holder.imageView.getContext(),data.getImagePath(),holder.imageView);

    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView imageView;

        public ImageHolder(@NonNull View view) {
            super(view);
            this.imageView = (AppCompatImageView) view;
        }
    }

}
