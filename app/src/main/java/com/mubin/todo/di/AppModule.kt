package com.mubin.todo.di

import android.content.Context
import androidx.room.Room
import com.mubin.todo.db.InternalDatabase
import com.mubin.todo.db.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



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