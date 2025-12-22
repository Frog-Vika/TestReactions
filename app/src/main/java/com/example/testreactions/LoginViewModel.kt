package com.example.testreactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val name = MutableLiveData("")
    val time = MutableLiveData(0L)

    /*fun authorize(): Boolean {
        return name.value == "Spock"
    }*/
}