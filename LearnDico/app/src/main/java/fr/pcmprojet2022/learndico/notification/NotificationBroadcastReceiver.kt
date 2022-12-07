package fr.pcmprojet2022.learndico.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import fr.pcmprojet2022.learndico.R


class NotificationBroadcastReceiver : BroadcastReceiver() {

    private val NOTIFICATION_CHANNEL_ID = "10001"
    private val CHANNEL_ID = "default"
    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        Log.wtf("---LOG : ", intent?.data.toString() +"---|")
        if (action == "swipe_notif") {
            Toast.makeText(context, "swipe_notif", Toast.LENGTH_SHORT).show()
            Log.wtf("NotificationBroadcastReceiver","Notification Broadcast Receiver")
            //TODO: req to add the word in db
            Toast.makeText(context, "$intent---", Toast.LENGTH_LONG).show()
        }else/* if (action == "recc_time")*/{

            notificationManager = context!!.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

            val notificationIntent = Intent(context, BroadcastReceiver::class.java)
            notificationIntent.putExtra("fromNotification", true)
                .flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("My Notification BIS")
                .setContentText("Notification Listener Service Example")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDeleteIntent(createDeleteIntent(context))
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContentIntent(
                    PendingIntent.getActivity(
                    context,
                    10/*notification id*/,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                ))
                .addAction(
                    0,
                    "Ouvrir",
                    getDismissIntent(10, context)
                )
                .build()

            notificationManager.notify(10, notification)

        }

    }

    private fun createDeleteIntent(context: Context): PendingIntent {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        intent.action = "swipe_notif"
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getDismissIntent(notificationId: Int, context: Context?): PendingIntent? {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("10"/*notification id*/, notificationId)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    }

}