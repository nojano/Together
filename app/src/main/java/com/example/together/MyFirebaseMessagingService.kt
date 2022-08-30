package com.example.together

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelID = "notification_channel"
const val channelname = "com.example.together"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // generate notification
    // attach the notification created with our custom layout
    // show the notification

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.getNotification() != null){
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }
    }


    fun getRemoteView(title: String, message: String) : RemoteViews {
        val remoteView = RemoteViews("com.example.together", R.layout.actvity_notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.logoApp,R.drawable.app_logo)

        return remoteView
    }

    fun generateNotification( title: String, message: String) {
        val intent = Intent(this, NotificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        //channel id, channel name
        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.app_logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))

        val notificationmanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelID, channelname, NotificationManager.IMPORTANCE_HIGH)
            notificationmanager.createNotificationChannel(notificationChannel)
        }

        notificationmanager.notify(0,builder.build())
    }
}