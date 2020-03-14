package com.cheise_proj.parent_feature.utils

import android.content.Context
import com.cheise_proj.parent_feature.R
import com.cheise_proj.presentation.model.vo.UserSession

data class GetNotificationNavigationUtil(val fcmName: String?, val fcmLevel: String?) {
    companion object {
        private const val NO_LOCATION = -1
    }

    fun getNavigation(context: Context, type: String?, userSession: UserSession): Int {
        with(userSession) {
            return when (type) {
                context.getString(R.string.notification_type_password_reset) -> {
                    if (fcmName == name) {
                        R.id.passwordFragment
                    } else {
                        NO_LOCATION
                    }
                }
                context.getString(R.string.notification_type_message) -> {
                    if (fcmLevel == level || fcmName == name) {
                        R.id.messageFragment
                    } else {
                        NO_LOCATION
                    }
                }
                context.getString(R.string.notification_type_report_image) -> {
                    if (fcmName == name) {
                        R.id.reportFragment2
                    } else {
                        NO_LOCATION
                    }
                }

                context.getString(R.string.notification_type_assignment_image) -> {
                    if (fcmLevel == level) {
                        R.id.assignmentFragment
                    } else {
                        NO_LOCATION
                    }
                }

                else -> NO_LOCATION
            }

        }
    }
}
