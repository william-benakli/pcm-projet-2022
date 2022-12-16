package fr.pcmprojet2022.learndico.notification

import android.net.Uri
import android.util.Log
import android.os.IBinder
import android.app.Service
import android.widget.Toast
import android.content.Intent
import android.content.Context
import android.app.PendingIntent
import fr.pcmprojet2022.learndico.R
import android.app.NotificationManager
import androidx.core.app.NotificationCompat

class ServiceNotification: Service() {

    private val NOTIFICATION_CHANNEL_ID = "10001"
    private val CHANNEL_ID = "channel"

    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    companion object {
        private var cpt=0
        private var swd=0
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent!=null){

            Log.wtf("Service", intent.action.toString())

            when (intent.action) {
                "open_notif" -> {
                    val iIntent = Intent(Intent.ACTION_VIEW, intent.data!!)
                    iIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.getIntExtra("idWord", 0)//TODO: rq bdd save swipe
                    startActivity(iIntent)
                    swd--
                    notificationManager.cancel(intent.getIntExtra("notification_id", 0))
                }
                "swipe_notif" -> {
                    intent.getIntExtra("idWord", 0)//TODO: rq bdd save swipe
                    swd--
                    Toast.makeText(this, "swipe_notif", Toast.LENGTH_LONG).show()
                }
                "run_notif" -> {
                    val shared = getSharedPreferences("params_learn_dico", Context.MODE_PRIVATE)
                    for (i in 0..shared.getInt("numNotification", 0)-swd){
                        val intentRq = Intent(this, ServiceNotification::class.java)
                        intentRq.action="open_notif"
                        intentRq.data= Uri.parse("https://google.com")//TODO: faire les rq a la bdd
                        intentRq.flags= Intent.FLAG_ACTIVITY_NEW_TASK
                        intentRq.putExtra("idWord", 0)

                        val dIntent = Intent(this, ServiceNotification::class.java)
                        dIntent.action = "swipe_notif"
                        dIntent.putExtra("idWord", 0)//TODO: get - rq - bdd

                        createNotification(intentRq, dIntent, "Title")//TODO: get info bdd
                    }
                    swd=shared.getInt("numNotification", 0)
                }
            }

            Log.wtf("ServiceNotification", intent.action.toString())

        }

        return START_STICKY
    }

    private fun createNotification(actionIntent: Intent, deletIntent: Intent, word: String){

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(word)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_launcher_foreground)//TODO: changer icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDeleteIntent(
                PendingIntent.getService(
                    this,
                    0,
                    deletIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .addAction(
                0,
                "Ouvrir",
                PendingIntent.getService(
                    this,
                    0,
                    actionIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()

        Log.wtf("SeviceNotification", cpt.toString())
        notificationManager.notify(cpt, notification)

        cpt++

    }

}