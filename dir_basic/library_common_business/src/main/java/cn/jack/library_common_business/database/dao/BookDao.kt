package cn.jack.library_common_business.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cn.jack.library_common_business.entiy.Book

/**
 * @创建者 Jack
 * @创建时间 2023/1/6 0006 15:57
 * @描述
 */
@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>
}