package com.mubin.todo.repository

import com.mubin.todo.api.Api
import com.mubin.todo.db.TaskDao
import com.mubin.todo.db.tables.TaskInfo
import java.util.Date
import javax.inject.Inject

class DataRepository

@Inject
constructor(
    private val api: Api,
    private val taskDao: TaskDao
)

{

    suspend fun getAllTask () : List<TaskInfo> = taskDao.getAllTaskFromInternalDb()
    suspend fun insertTask (task: TaskInfo) : Long = taskDao.insertTask(task)
    suspend fun updateTask (isDone: Boolean, id: Int) : Int = taskDao.updateTask(isDone, id)
    suspend fun getTasksByDate () : List<TaskInfo> = taskDao.getTasksByDate()
    suspend fun getDataFromRemote() = api.getTvShows()


}