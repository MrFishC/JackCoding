package cn.jack.library_image.glide;

import android.content.Context;
import android.widget.ImageView;

import cn.jack.library_image.image.IImageLoader;

/**
 * @创建者 Jack
 * @创建时间 2021/3/11 11:15
 * @描述 glide图片加载的管理者
 */
public class GlideManager implements IImageLoader {

    private GlideManager() {

    }

    private GlideManager(int loadingResId, int loadErrorResId) {
        //初始化GlideUtil
        GlideUtil.getInstance().initDefaultResource(loadingResId,loadErrorResId);
    }

    @Override
    public void loadImageByNet(Context context, String url, ImageView imageView) {
        GlideUtil.getInstance().loadImageByNet(context,url,imageView);
    }

    /**
     * 构建者模式
     */
    public static class Builder {

        /**
         * 占位图-加载中显示
         */
        private int mLoadingResId;

        /**
         * 占位图-加载错误时显示
         */
        private int mLoadErrorResId;

        public Builder setLoadingResId(int loadingResId) {
            mLoadingResId = loadingResId;
            return this;
        }

        public Builder setLoadErrorResId(int loadErrorResId) {
            mLoadErrorResId = loadErrorResId;
            return this;
        }

        public GlideManager create(){
            return new GlideManager(mLoadingResId,mLoadErrorResId);
        }

    }

}
