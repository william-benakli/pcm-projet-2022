package fr.pcmprojet2022.learndico.notification

import android.util.Log
import android.os.IBinder
import android.app.Service
import android.widget.Toast
import android.content.Intent
import android.app.NotificationManager


class ServiceNotification: Service() {


    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent!=null){

            Log.wtf("Service", intent.action.toString())

            if(intent.action=="open_notif"){

                val iIntent = Intent(Intent.ACTION_VIEW, intent.data!!)
                iIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(iIntent)

                notificationManager.cancel(intent.getIntExtra("notification_id", 0))

            }else if(intent.action=="swipe_notif"){
                Toast.makeText(this, "swipe_notif", Toast.LENGTH_LONG).show()
            }

        }

        return super.onStartCommand(intent, flags, startId)
    }



}