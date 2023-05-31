package com.mubin.todo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubin.todo.db.tables.TaskInfo
import com.mubin.todo.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel
@Inject
constructor(private val repository: DataRepository): ViewModel() {

    fun getAllTasks(): LiveData<List<TaskInfo>> {

        val dataList = MutableLiveData<List<TaskInfo>>()
        viewModelScope.launch(Dispatchers.IO) {

            val data = repository.getAllTask()

            withContext(Dispatchers.Main) {

                dataList.value = data

            }

        }

        return dataList

    }

    fun insertTask(taskInfo: TaskInfo): LiveData<Long> {

        val response = MutableLiveData<Long>()

        viewModelScope.launch(Dispatchers.IO) {

            val responseBody = repository.insertTask(taskInfo)

            withContext(Dispatchers.Main) {
                response.value = responseBody
            }

        }

        return response

    }

    fun updateTask(id: Int, isDone: Boolean) : LiveData<Int> {

        val response= MutableLiveData<Int>()

        viewModelScope.launch(Dispatchers.IO) {

            val responseData = repository.updateTask(isDone, id)

            withContext(Dispatchers.Main) {

                response.value = responseData

            }

        }

        return response

    }

}