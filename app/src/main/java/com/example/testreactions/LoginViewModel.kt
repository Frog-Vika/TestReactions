package com.example.testreactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    // Имя пользователя
    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    // Лучшее время (рекорд)
    private val _time = MutableLiveData(0L)
    val time: LiveData<Long> get() = _time

    fun setName(newName: String) {
        _name.value = newName
    }

    // Логика сохранения рекорда
    fun saveBestTime(result: Long) {
        val currentRecord = _time.value ?: 0L
        if (currentRecord == 0L || result < currentRecord) {
            _time.value = result
        }
    }
}

/*package com.example.testreactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val name = MutableLiveData("")
    val time = MutableLiveData(0L)

    /*fun authorize(): Boolean {
        return name.value == "Spock"
    }*/
}*/