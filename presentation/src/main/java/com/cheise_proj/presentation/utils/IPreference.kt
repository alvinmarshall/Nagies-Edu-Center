package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.vo.UserSession

interface IPreference {
    fun setUserSession(userSession: UserSession)
    fun getUserSession(): UserSession
    fun setBackgroundChanger(set:Boolean)
    fun getFirstTimeLogin():Boolean
    fun getBackgroundChanger():Boolean
    fun setFirstTimeLogin(set:Boolean)
}