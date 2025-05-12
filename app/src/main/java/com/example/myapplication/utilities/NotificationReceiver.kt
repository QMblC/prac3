package com.example.myapplication.utilities

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (!Notification.areNotificationsEnabled(context)) {
                Log.w("NotificationReceiver", "Notifications are disabled in system settings")
                return
            }

            if (!Notification.hasNotificationPermission(context)) {
                Log.w("NotificationReceiver", "Notification permission not granted")
                return
            }

            val name = intent.getStringExtra("profile_name") ?: "Пользователь"
            val notificationId = 1

            val launchIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pi = PendingIntent.getActivity(
                context, 0, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notif = NotificationCompat.Builder(context, Notification.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Время любимой пары")
                .setContentText("$name, Ваша пара началась!")
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.notify(notificationId, notif)
        } catch (e: Exception) {
            Log.e("NotificationReceiver", "Error showing notification", e)
        }
    }
}