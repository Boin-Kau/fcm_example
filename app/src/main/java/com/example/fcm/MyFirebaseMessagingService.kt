package com.example.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    /*
    <토큰 생성 모니터링>
        토큰은 수시로 변경됨
        따라서, onNewToken 메서드가 호출될 때마다 ( 토큰이 갱신될 때마다 )
        서버에 해당 토큰을 갱신해줘야 함.
     */
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val type = remoteMessage.data["type"]
            ?.let { NotificationType.valueOf(it) }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        // type 이 Null 일 경우, 알림 생성 x
        type ?: return

        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type, title, message))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification(
        type : NotificationType,
        title : String?,
        message : String?)
    : Notification {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        when(type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            "😀🥰🙂😰😨😡😺💩👺🤮👿😺🧑🏽‍🌾👨🏻‍💼🙋🏼‍♂️🥼🩱🧣🪱🐡"
                        )
                )
            }
            NotificationType.CUSTOM -> {

            }
        }

        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Emoji Party를 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
    }
}