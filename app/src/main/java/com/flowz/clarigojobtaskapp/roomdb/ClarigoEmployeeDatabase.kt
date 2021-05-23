package com.flowz.clarigojobtaskapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.model.UriConverters

@Database(entities = [ClarigoEmployee::class], version = 1, exportSchema = false)
@TypeConverters(UriConverters::class)
abstract class ClarigoEmployeeDatabase : RoomDatabase(){

    abstract fun ClarigoEmployeeDao(): ClarigoEmployeeDao

}