package com.mubin.todo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mubin.todo.db.tables.TaskInfo
import com.mubin.todo.helper.DateConverter

@Database(entities = [TaskInfo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class InternalDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

}