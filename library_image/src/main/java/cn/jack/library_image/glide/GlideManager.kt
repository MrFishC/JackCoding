package cn.jack.library_image.glide

import android.content.Context
import android.widget.ImageView
import cn.jack.library_image.image.IImageLoader

/**
 * @创建者 Jack
 * @创建时间 2021/3/11 11:15
 * @描述 glide图片加载的管理者
 */
internal class GlideManager private constructor() : IImageLoader {
    constructor(loadingResId: Int, loadErrorResId: Int) : this() {
        //初始化GlideUtil
        GlideUtil.getInstance().initDefaultResource(loadingResId, loadErrorResId)
    }

    override fun loadImageByNet(context: Context, url: String, imageView: ImageView) {
        GlideUtil.getInstance().loadImageByNet(context, url, imageView)
    }

    /**
     * 构建者模式
     */
    class Builder {
        /**
         * 占位图-加载中显示
         */
        private var mLoadingResId = 0

        /**
         * 占位图-加载错误时显示
         */
        private var mLoadErrorResId = 0
        fun setLoadingResId(loadingResId: Int): Builder {
            mLoadingResId = loadingResId
            return this
        }

        fun setLoadErrorResId(loadErrorResId: Int): Builder {
            mLoadErrorResId = loadErrorResId
            return this
        }

        fun create(): GlideManager {
            return GlideManager(mLoadingResId, mLoadErrorResId)
        }
    }
}