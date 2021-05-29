package com.flowz.clarigojobtaskapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee

class Editable {

    companion object {
        val EMPLOYEE1: MutableLiveData<ClarigoEmployee>? = null
        var EMPLOYEE: ClarigoEmployee? = null
    }
}