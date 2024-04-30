package cn.jack.module_fragment_01.mvvm.model.entity

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 15:56
 * @描述 轮播图信息
 *
 * 问题：定义成BannerInfos 无法正常使用 暂不知道原因
 */
data class BanInfos(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)
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
