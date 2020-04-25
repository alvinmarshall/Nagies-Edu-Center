package com.cheise_proj.parent_feature.notification

import com.cheise_proj.parent_feature.utils.GetNotificationNavigationUtil
import com.cheise_proj.presentation.notification.IParentNotification
import com.cheise_proj.presentation.utils.IPreference
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import javax.inject.Inject

class FirebaseParentMessageService : FirebaseMessagingService() {
    @Inject
    lateinit var notification: IParentNotification

    @Inject
    lateinit var prefs: IPreference

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        println("data ${p0.data}")
        val data = p0.data
        val getDirection = GetNotificationNavigationUtil(data["name"], data["level"])
        val direction =
            getDirection.getNavigation(applicationContext, data["type"], prefs.getUserSession())
        if (direction < 0) return
        notification.initNotification(
            applicationContext,
            data["title"] ?: "",
            data["body"] ?: "",
            direction
        )
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        println("refresh token $p0")
    }

}