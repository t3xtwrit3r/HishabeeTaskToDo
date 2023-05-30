package com.mubin.todo.db.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "TaskInfo")
data class TaskInfo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "TaskTitle")
    var appName: String? = "",

    @ColumnInfo(name = "TaskDetails")
    var packageName: String? = "",

    @ColumnInfo(name = "TaskDate")
    var taskDate: Date?,

    @ColumnInfo(name = "Priority")
    var Priority: String? = "",

    @ColumnInfo(name = "PriorityLevel")
    var PriorityLevel: Int? = null

)
