package cn.jack.library_image.image;

/**
 * @创建者 Jack
 * @创建时间 2021/3/11 11:11
 * @描述 图片加载管理者
 */
public class ImageManager {

    private IImageLoader mImageLoader;

    /**
     * 单例 - 静态内部类
     */
    public static ImageManager getInstance() {
        return ImageManager.Holder.INSTANCE;
    }

    private static class Holder {
        private static final ImageManager INSTANCE = new ImageManager();
    }

    private ImageManager() {

    }

    /**
     * 初始化图片加载框架，建议在application调用
     * @param imageLoader 指定的图片加载框架
     */
    public void init(IImageLoader imageLoader){
        this.mImageLoader = imageLoader;
    }

    /**
     * 供给外部调用
     */
    public IImageLoader getImageLoader() {
        if(mImageLoader == null){
            throw new RuntimeException("IImageLoader's init function must be call frist!");
        }
        return mImageLoader;
    }

}
