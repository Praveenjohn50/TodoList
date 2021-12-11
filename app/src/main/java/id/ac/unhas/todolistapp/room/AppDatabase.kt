package id.ac.unhas.todolistapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.unhas.todolistapp.room.todo.Todo
import id.ac.unhas.todolistapp.room.todo.TodoDao
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration

/**
 * Created by Praveen John on 11/12/2021
 * Room database
 * */
@Database(entities = [Todo::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {

        private var instance: AppDatabase? = null

        /**
         * migration way without dataloss
         * */
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration of database can be done by this way
            }
        }

        /**
         * Get database Method
         * */
        fun getDatabase(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "TODO_DB"
                    )
                        .build()
                }
            }
            return instance
        }
    }
}