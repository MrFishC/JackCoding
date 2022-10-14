package cn.jack.library_image.util

import android.widget.ImageView
import cn.jack.library_image.glide.GlideManager
import cn.jack.library_image.image.ImageManager

/**
 * @创建者 Jack
 * @创建时间 2022/9/16 11:27
 * @描述 直接调用该类的Api去加载图片即可
 */
class ImageU {
    companion object {
        fun init(loadingResId: Int = 0, loadErrorResId: Int = 0) {
            val gm = GlideManager.Builder()
                .setLoadingResId(loadingResId)
                .setLoadErrorResId(loadErrorResId)
                .create()
            ImageManager.getInstance().init(gm)
        }

        fun loadByNet(view: ImageView, url: String) {
            ImageManager.getInstance().getImageLoader().loadImageByNet(view.context, url, view)
        }
    }
}