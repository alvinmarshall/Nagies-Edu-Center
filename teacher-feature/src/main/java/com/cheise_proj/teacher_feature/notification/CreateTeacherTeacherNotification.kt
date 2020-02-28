package com.cheise_proj.teacher_feature.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.cheise_proj.presentation.notification.ITeacherNotification
import com.cheise_proj.teacher_feature.BuildConfig
import com.cheise_proj.teacher_feature.R
import com.cheise_proj.teacher_feature.TeacherNavigationActivity
import javax.inject.Inject

class CreateTeacherTeacherNotification @Inject constructor() :
    ITeacherNotification {
    companion object {
        private const val CHANNEL_ID = "${BuildConfig.LIBRARY_PACKAGE_NAME}_channel"
        private const val CHANNEL_NAME = "teacher"
    }

    override fun initNotification(
        context: Context,
        title: String,
        message: String,
        destination: Int
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.teacher_navigation)
            .setComponentName(TeacherNavigationActivity::class.java)
            .setDestination(destination)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
        val id = kotlin.math.floor(Math.random() * 20).toInt()
        notificationManager.notify(id, notification.build())
    }


}