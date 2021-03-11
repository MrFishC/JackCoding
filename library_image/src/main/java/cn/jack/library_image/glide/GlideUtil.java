package cn.jack.library_image.glide;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * @创建者 Jack
 * @创建时间 2021/3/11 11:22
 * @描述 图片加载工具类-glide框架实现
 */
public class GlideUtil {

    private int mLoadingResId;

    private int mLoadErrorResId;

    /**
     * 单例-静态内部类
     */
    public static GlideUtil getInstance() {
        return GlideUtil.Holder.INSTANCE;
    }

    private static class Holder {
        private static final GlideUtil INSTANCE = new GlideUtil();
    }

    private GlideUtil() {

    }

    public void initDefaultResource(int loadingResId,int loadErrorResId){
        this.mLoadingResId = loadingResId;
        this.mLoadErrorResId = loadErrorResId;
    }

    // ====================================== start ======================================
    public void loadImageByNet(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
    // ======================================  end  ======================================

}
