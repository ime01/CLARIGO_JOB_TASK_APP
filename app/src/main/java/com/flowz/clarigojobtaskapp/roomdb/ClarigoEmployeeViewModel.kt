package com.flowz.clarigojobtaskapp.roomdb

import android.util.Log
import androidx.lifecycle.*
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.ui.ClarigoEmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel()
class ClarigoEmployeeViewModel  @Inject constructor(private val ceRpository : ClarigoEmployeeRepository): ViewModel(){

    val ceEmployeesFromDb = ceRpository.ClarigoEmployeesFromdb

    fun insertClarigoEmployee(clarigoEmployee: ClarigoEmployee){

        viewModelScope.launch (Dispatchers.IO){
            ceRpository.insertClarigoEmployee(clarigoEmployee)
        }
    }

    fun updateClarigoEmployee(clarigoEmployee: ClarigoEmployee){

        viewModelScope.launch (Dispatchers.IO){
            ceRpository.updateClarigoEmployee(clarigoEmployee)
        }
    }

    fun deleteClarigoEmployee(clarigoEmployee: ClarigoEmployee){

        viewModelScope.launch (Dispatchers.IO){
            ceRpository.deleteClarigoEmployee(clarigoEmployee)
        }
    }

    fun searchClarigoEmployee(searchQuery: String): LiveData<List<ClarigoEmployee>>{
        return ceRpository.searchClarigoEmployee(searchQuery).asLiveData()
    }



}