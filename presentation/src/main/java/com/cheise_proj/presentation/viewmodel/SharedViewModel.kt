package com.cheise_proj.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel :ViewModel(){
    private val badgeValue:MutableLiveData<Pair<Int,Int?>> = MutableLiveData()

    fun setBadgeValue(badge:Pair<Int,Int?>){
        badgeValue.value = badge
    }

    fun getBadgeValue():LiveData<Pair<Int,Int?>> = badgeValue
}