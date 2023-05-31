package com.mubin.todo.workManger

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.haroldadmin.cnradapter.NetworkResponse
import com.mubin.appscheduler.api.models.tv_show.TvShowsModelItem
import com.mubin.todo.db.tables.TaskInfo
import com.mubin.todo.helper.Constants.exhaustive
import com.mubin.todo.repository.DataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltWorker
class DataSync
@AssistedInject constructor(@Assisted context: Context,@Assisted workerParams: WorkerParameters,private val repository: DataRepository) : Worker(context, workerParams) {

    override fun doWork(): Result {
        syncDataFromRemote()
        return Result.success()
    }

    private fun syncDataFromRemote() {
        try {

            val responseBody = MutableLiveData<List<TvShowsModelItem>>()
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.getDataFromRemote()
                withContext(Dispatchers.Main) {
                    when (response) {
                        is NetworkResponse.Success -> {
                            responseBody.value = response.body
                            Log.d("DataFromRemoteWorker", "${responseBody.value}")
                        }
                        is NetworkResponse.ServerError -> {
                            val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        }
                        is NetworkResponse.NetworkError -> {
                            val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        }
                        is NetworkResponse.UnknownError -> {
                            val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        }
                    }.exhaustive
                }
            }

        } catch (e: java.lang.Exception) {
            Log.d("ExceptionWorker","${e.message}")
        }

    }


}