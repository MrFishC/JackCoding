package cn.jack.library_common_business.entiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @创建者 Jack
 * @创建时间 2023/1/5 0005 18:30
 * @描述
 */
@Entity
data class HistorySearchInfo(
    @ColumnInfo val key: String
) {
    //自增长
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
}