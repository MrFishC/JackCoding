package cn.jack.library_common_business.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.jack.library_common_business.database.dao.BookDao
import cn.jack.library_common_business.database.dao.HistorySearchDao
import cn.jack.library_common_business.entiy.Book
import cn.jack.library_common_business.entiy.HistorySearchInfo

/**
 * @创建者 Jack
 * @创建时间 2023/1/5 0005 19:29
 * @描述
 */
//注解指定了database的表映射实体数据以及版本等信息
@Database(entities = [HistorySearchInfo::class, Book::class], version = Constants.DATABASE_VERSION)
abstract class HistorySearchInfoDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: HistorySearchInfoDatabase? = null

        //数据库版本升级模板
        val MIGRATION_2_3 = object: Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase){
                database.execSQL("create table Book (id integer primary key autoincrement not null,name text not null, pages integer not null)")
            }
        }

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): HistorySearchInfoDatabase {
            if (instance == null) {
                instance = create(context)
            }
            return instance!!
        }

        private fun create(context: Context): HistorySearchInfoDatabase {
            return Room.databaseBuilder(context, HistorySearchInfoDatabase::class.java, Constants.DB_NAME).addMigrations(MIGRATION_2_3)
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun getHistorySearchDao(): HistorySearchDao
    abstract fun bookDao(): BookDao
}

object Constants {
    //数据库名称
    const val DB_NAME = "HistorySearchInfo.db"

    //数据库版本
    const val DATABASE_VERSION = 3
}