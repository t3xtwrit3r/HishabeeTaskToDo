package com.mubin.todo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mubin.todo.db.tables.TaskInfo
import java.util.Date

@Dao
interface TaskDao {

    @Query("Select * FROM TaskInfo")
    suspend fun getAllTaskFromInternalDb(): List<TaskInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskInfo): Long

    @Query("select * from TaskInfo ORDER BY TaskDate")
    suspend fun getTasksByDate(): List<TaskInfo>


}