package cn.jack.library_image.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @创建者 Jack
 * @创建时间 2021/3/11 11:22
 * @描述 图片加载工具类-glide框架实现
 */
internal class GlideUtil private constructor() {
    private var mLoadingResId = 0
    private var mLoadErrorResId = 0

    companion object {
        @Volatile
        private var instance: GlideUtil? = null

        fun getInstance(): GlideUtil = instance ?: synchronized(this) {
            instance ?: GlideUtil().also { instance = it }
        }
    }

    fun initDefaultResource(loadingResId: Int, loadErrorResId: Int) {
        mLoadingResId = loadingResId
        mLoadErrorResId = loadErrorResId
    }

    // ====================================== start ======================================
    fun loadImageByNet(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }
    // ======================================  end  ======================================

}