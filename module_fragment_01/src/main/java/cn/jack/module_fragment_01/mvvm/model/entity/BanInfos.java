package cn.jack.module_fragment_01.mvvm.model.entity;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 15:56
 * @描述 轮播图信息
 *
 * 问题：定义成BannerInfos 无法正常使用 暂不知道原因
 *
 */
public class BanInfos {
    /**
     * desc : 扔物线
     * id : 29
     * imagePath : https://wanandroid.com/blogimgs/8a0131ac-05b7-4b6c-a8d0-f438678834ba.png
     * isVisible : 1
     * order : 0
     * title : 声明式 UI？Android 官方怒推的 Jetpack Compose 到底是什么？
     * type : 0
     * url : http://i0k.cn/4WyJG
     */

    private String desc;
    private Integer id;
    private String  imagePath;
    private Integer isVisible;
    private Integer order;
    private String  title;
    private Integer type;
    private String  url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", isVisible=" + isVisible +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }
}
