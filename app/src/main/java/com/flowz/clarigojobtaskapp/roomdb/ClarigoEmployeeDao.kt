package com.flowz.clarigojobtaskapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.model.UriConverters
import kotlinx.coroutines.flow.Flow

@Dao
interface ClarigoEmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClarigoEmployee(employee: ClarigoEmployee)

    @Delete
    suspend fun deleteClarigoEmployee(employee: ClarigoEmployee)

    @Update
    suspend fun updateClarigoEmployee(employee: ClarigoEmployee)

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM clarigoemployee_table")
    fun getAllClarigoEmployees(): LiveData<List<ClarigoEmployee>>

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM clarigoemployee_table WHERE name LIKE :clarigoEmployeeName")
    fun searchClarigoEmployees(clarigoEmployeeName: String): Flow<List<ClarigoEmployee>>


}