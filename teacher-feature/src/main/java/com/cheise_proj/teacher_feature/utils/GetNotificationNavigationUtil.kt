package com.cheise_proj.teacher_feature.utils

import android.content.Context
import com.cheise_proj.presentation.model.vo.UserSession
import com.cheise_proj.teacher_feature.R

data class GetNotificationNavigationUtil(val fcmName: String?, val fcmLevel: String?) {
    companion object {
        private const val NO_LOCATION = -1
    }

    fun getNavigation(context: Context, type: String, userSession: UserSession): Int {
        with(userSession) {
            return when (type) {
                context.getString(R.string.notification_type_password_reset) -> {
                    if (fcmName == name) {
                        R.id.passwordFragment
                    } else {
                        NO_LOCATION
                    }
                }

                context.getString(R.string.notification_type_complaint) -> {
                    if (fcmName == name) {
                        R.id.complaintFragment
                    } else {
                        NO_LOCATION
                    }
                }

                else -> NO_LOCATION
            }

        }
    }
}
