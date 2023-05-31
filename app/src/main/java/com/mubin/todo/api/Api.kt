package com.mubin.todo.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.mubin.appscheduler.api.models.ErrorResponse
import com.mubin.appscheduler.api.models.tv_show.TvShowsModelItem
import com.mubin.todo.helper.Constants
import retrofit2.http.GET

interface Api {

    @GET(Constants.END_POINT)
    suspend fun getTvShows(): NetworkResponse<List<TvShowsModelItem>, ErrorResponse>

}