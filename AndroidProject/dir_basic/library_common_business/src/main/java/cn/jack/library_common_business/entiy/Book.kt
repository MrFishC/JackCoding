package cn.jack.library_common_business.entiy

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @创建者 Jack
 * @创建时间 2023/1/6 0006 15:56
 * @描述
 */
@Entity
data class Book(var name: String, var pages: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}