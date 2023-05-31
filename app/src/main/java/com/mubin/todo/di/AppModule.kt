package com.mubin.todo.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mubin.todo.api.Api
import com.mubin.todo.api.RetrofitUtils.retrofitInstance
import com.mubin.todo.db.InternalDatabase
import com.mubin.todo.db.TaskDao
import com.mubin.todo.helper.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("apiMovies")
    fun provideBaseUrlMovies1() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance1(@Named("apiMovies") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): Api =
        retrofitInstance(baseUrl = BASE_URL, gson, httpClient)
            .create(Api::class.java)

    @Provides
    @Singleton
    @Named("db1")
    fun buildDatabase(@ApplicationContext context: Context) : InternalDatabase {
        return Room.databaseBuilder(context, InternalDatabase::class.java, "internal_db").build()
    }

    @Provides
    @Singleton
    fun getAppDao(@Named("db1") internalDatabase: InternalDatabase): TaskDao {
        return internalDatabase.getTaskDao()
    }

}