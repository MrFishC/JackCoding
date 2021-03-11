package cn.jack.library_image.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * @创建者 Jack
 * @创建时间 2021/3/11 11:10
 * @描述 定义图片加载的方法
 */
public interface IImageLoader {
    void loadImageByNet(Context context,String url,ImageView imageView);
}
