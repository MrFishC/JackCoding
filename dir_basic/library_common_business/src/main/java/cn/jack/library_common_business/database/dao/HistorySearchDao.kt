package cn.jack.library_common_business.database.dao

import androidx.room.*
import cn.jack.library_common_business.entiy.HistorySearchInfo

/**
 * @创建者 Jack
 * @创建时间 2023/1/5 0005 18:33
 * @描述
 */
@Dao
interface HistorySearchDao {
    //所有的CURD根据primary key进行匹配
    //简单sql语句，查询 HistorySearchInfo 表所有的column
    @Query("SELECT * FROM HistorySearchInfo")
    fun getAll(): List<HistorySearchInfo>

    // OnConflictStrategy.REPLACE表示如果已经有数据，那么就覆盖掉
    //数据的判断通过主键进行匹配，也就是uid，非整个historySearchInfo对象
    //返回Long数据表示，插入条目的主键值（uid）
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historySearchInfo: HistorySearchInfo): Long

    @Delete
    fun delete(historySearchInfo: HistorySearchInfo)

    @Delete
    fun deleteAll(historySearchInfos: List<HistorySearchInfo>)
}