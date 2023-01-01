package fr.pcmprojet2022.learndico.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import kotlin.concurrent.thread

class ServiceNotification: LifecycleService() {

    private val NOTIFICATION_CHANNEL_ID = "10001"
    private val CHANNEL_ID = "channel"

    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    private val database by lazy{ LearnDicoBD.getInstanceBD(this);}

    //private val dao = (application as MainActivity).database.getRequestDao()

    companion object {
        private var cpt=0
        private var swd=0
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.wtf("ON START ", "################################")
        if (intent!=null){

            Log.wtf("Service", intent.action.toString())

            when (intent.action) {
                "open_notif" -> {
                    val iIntent = Intent(Intent.ACTION_VIEW, intent.data!!)
                    iIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(iIntent)//RÉCUPÉRER URL DE LA PAGE
                    swd--
                    notificationManager.cancel(intent.getIntExtra("notification_id", 0))
                }
                "swipe_notif" -> {

                    //TODO: RÉCUPÉRER ID
                    var swip = 0
                    thread {
                        val wordRq = database.getRequestDao().getWordByKey(intent.getStringExtra("idWord").toString())
                        if (wordRq != null) {
                            Log.wtf("test", wordRq.remainingUses.toString())
                        }
                        if (wordRq!=null){
                            wordRq.remainingUses-=1
                            database.getRequestDao().updateWord(wordRq)
                            swip = wordRq.remainingUses
                        }
                    }
                    swd--
                    Toast.makeText(this, "Vous avez validé ce mot $swip/10", Toast.LENGTH_LONG).show()
                }
                "run_notif" -> {
                    val shared = getSharedPreferences("params_learn_dico", Context.MODE_PRIVATE)
                    database.getRequestDao().loadAllWordsAvailableNotif().observe(this){
                        val list = it.toMutableList()
                        for(item in list){
                            Log.wtf("element " , item.wordOrigin)
                            Log.wtf("element " , item.remainingUses.toString())
                        }
                        val nbrElement = shared.getInt("numNotification", 0)-swd
                        val randomElements = list.asSequence().shuffled().take(nbrElement).toList()//list de mots qu'on va envoyer à l'utilisateur

                        /*Log.wtf("Random El", randomElements.toString())
                        Log.wtf("SIZE", list.toString())

                        Log.wtf("SIZE", randomElements.toString())*/

                        for (i in randomElements.indices){
                            val intentRq = Intent(this, ServiceNotification::class.java)
                            intentRq.action="open_notif"
                            intentRq.data= Uri.parse(randomElements[i].url)//RÉCUPÉRER LIEN
                            intentRq.flags= Intent.FLAG_ACTIVITY_NEW_TASK
                            intentRq.putExtra("idWord", randomElements[i].url)//récupérer identifiant unique du mot
                            intentRq.putExtra("notification_id", cpt)

                            val dIntent = Intent(this, ServiceNotification::class.java)
                            dIntent.action = "swipe_notif"
                            dIntent.putExtra("idWord", randomElements[i].url)//RÉCUPÉRER identifiant unique du mot

                            createNotification(intentRq, dIntent, randomElements[i].wordOrigin)//RÉCUPÉRER MOT
                        }
                        swd=shared.getInt("numNotification", 0)
                    }
                }
            }

            /*Log.wtf("ServiceNotification", intent.action.toString())*/

        }

        return START_NOT_STICKY
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
                    cpt,
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