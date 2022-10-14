package cn.jack.module_fragment_03.entity

/**
 * @创建者 Jack
 * @创建时间 2022/9/17 18:33
 * @描述
 */
data class SystemInfo(
    val articleList: List<Any>,
    val author: String,
    val children: List<Children>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val type: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)