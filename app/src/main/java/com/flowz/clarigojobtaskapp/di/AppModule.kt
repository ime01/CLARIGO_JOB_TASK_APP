package com.flowz.clarigojobtaskapp.di

import android.content.Context
import androidx.room.Room
import com.flowz.clarigojobtaskapp.roomdb.ClarigoEmployeeDatabase
import com.flowz.clarigojobtaskapp.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesClarigoEmployeeDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, ClarigoEmployeeDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
   fun providesClarigoEmployeeDao(db: ClarigoEmployeeDatabase) = db.ClarigoEmployeeDao()



}