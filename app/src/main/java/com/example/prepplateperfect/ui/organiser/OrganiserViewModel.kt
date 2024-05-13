package com.example.prepplateperfect.ui.organiser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrganiserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Organiser Fragment"
    }
    val text: LiveData<String> = _text
}