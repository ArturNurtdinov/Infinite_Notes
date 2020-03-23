package com.infinitevoid.infinitenotes.newnotes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.infinitevoid.infinitenotes.R

class NotificationReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "1"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Delayed notifications"
            val descriptionText = "This channel is for delayed notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val content = intent!!.extras?.getString(NewnoteFragment.CONTENT_KEY)

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setChannelId(CHANNEL_ID)
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }
}