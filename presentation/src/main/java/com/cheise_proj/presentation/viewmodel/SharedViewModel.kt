package com.cheise_proj.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel :ViewModel(){
    private val badgeValue:MutableLiveData<String> = MutableLiveData()

    fun setBadgeValue(badge:String){
        badgeValue.value = badge
    }

    fun getBadgeValue():LiveData<String> = badgeValue
}