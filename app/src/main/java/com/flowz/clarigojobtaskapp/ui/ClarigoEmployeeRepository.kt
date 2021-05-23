package com.flowz.clarigojobtaskapp.ui

import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.roomdb.ClarigoEmployeeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClarigoEmployeeRepository @Inject constructor(private val dao: ClarigoEmployeeDao){

    val ClarigoEmployeesFromdb = dao.getAllClarigoEmployees()

    suspend fun insertClarigoEmployee(clarigoEmployee: ClarigoEmployee){
        dao.insertClarigoEmployee(clarigoEmployee)
    }

    suspend fun updateClarigoEmployee(clarigoEmployee: ClarigoEmployee){
        dao.updateClarigoEmployee(clarigoEmployee)
    }

    suspend fun deleteClarigoEmployee(clarigoEmployee: ClarigoEmployee){
        dao.deleteClarigoEmployee(clarigoEmployee)
    }


    fun searchClarigoEmployee(searchQuery: String): Flow<List<ClarigoEmployee>>{
        return dao.searchClarigoEmployees(searchQuery)
    }




}