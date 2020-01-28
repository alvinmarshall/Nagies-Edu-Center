package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.vo.UserSession

interface IPreference {
    fun setUserSession(userSession: UserSession)
    fun getUserSession(): UserSession
}