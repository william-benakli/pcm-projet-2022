package fr.pcmprojet2022.learndico.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import android.widget.Toast
import fr.pcmprojet2022.learndico.MainActivity


class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == "swipe_notif") {
            Toast.makeText(context, "swipe_notif", Toast.LENGTH_SHORT).show()
            Log.wtf("NotificationBroadcastReceiver","Notification Broadcast Receiver")
            //TODO: req to add the word in db
            Toast.makeText(context, "$intent---", Toast.LENGTH_LONG).show()
        }else{
            Log.wtf("LOG : ", intent?.data.toString() +"---")
            Toast.makeText(context, intent?.getStringExtra("link").toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(context, intent.toString(), Toast.LENGTH_SHORT).show()
        }
        /*Log.wtf("---LOG : ", "$intent---")

        context!!.let {
            it.startActivity(Intent (it, MainActivity::class.java))
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, intent?.data)
        browserIntent.flags = FLAG_ACTIVITY_NEW_TASK
        context!!.startActivity(browserIntent)*/

        /*val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel( 10 )*/

    }

}