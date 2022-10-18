package cn.jack.module_fragment_01.mvvm.view.adapter

import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos
import com.youth.banner.adapter.BannerAdapter
import cn.jack.module_fragment_01.mvvm.view.adapter.ImageNetAdapter.ImageHolder
import android.view.ViewGroup
import com.youth.banner.util.BannerUtils
import cn.jack.module_fragment_01.R
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import cn.jack.library_image.util.ImageU

/**
 * 自定义布局，网络图片
 */
class ImageNetAdapter(mDatas: List<BanInfos>) : BannerAdapter<BanInfos, ImageHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView =
            BannerUtils.getView(parent, R.layout.module_fragment_01_layout_banner_image) as AppCompatImageView
        //通过裁剪实现圆角
        BannerUtils.setBannerRound(imageView, 20f)
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder, data: BanInfos, position: Int, size: Int) {
        ImageU.loadByNet(holder.imageView, data.imagePath)
    }

    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: AppCompatImageView

        init {
            imageView = view as AppCompatImageView
        }
    }

}