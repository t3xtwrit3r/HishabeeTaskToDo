package com.mubin.todo.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "TaskInfo")
data class TaskInfo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "TaskTitle")
    var taskTitle: String? = "",

    @ColumnInfo(name = "TaskDetails")
    var taskDetails: String? = "",

    @ColumnInfo(name = "TaskDate")
    var taskDate: Date?,

    @ColumnInfo(name = "Priority")
    var priority: String? = "",

    @ColumnInfo(name = "PriorityLevel")
    var priorityLevel: Int? = null,

    @ColumnInfo(name = "isDone")
    var isDone: Boolean? = null

)
