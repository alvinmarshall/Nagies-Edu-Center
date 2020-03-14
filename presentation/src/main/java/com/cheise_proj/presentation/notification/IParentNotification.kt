package com.cheise_proj.presentation.notification

import android.content.Context

interface IParentNotification {
    fun initNotification(context: Context, title: String, message: String, destination: Int)
}