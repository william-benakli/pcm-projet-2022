package fr.pcmprojet2022.learndico

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.databinding.ActivityMainBinding
import fr.pcmprojet2022.learndico.notification.NotificationBroadcastReceiver


class MainActivity : AppCompatActivity() {

    private val NOTIFICATION_CHANNEL_ID = "10001"
    private val CHANNEL_ID = "default"

    private val NOTIF_PERMISSION_CODE = 100


    val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    val database by lazy{LearnDicoBD.getInstanceBD(this);}
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NavigationUI: automatisation de la gestion des multi backstacks
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = (navHostFragment as NavHostFragment).findNavController()
        binding.menuNavigation.setupWithNavController(navController)

        //permets de conserver plusieurs piles tout en navigant vers le bon fragment
        binding.menuNavigation.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }

        createNotificationChannel()

        createNotification()

        requestPermission()

    }

    private fun createNotification() {
        Log.wtf("createNotification","creation of the notification")

        val notificationIntent = Intent(this, BroadcastReceiver::class.java)
        notificationIntent.putExtra("fromNotification", true)
            .flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("My Notification")
            .setContentText("Notification Listener Service Example")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDeleteIntent(createDeleteIntent())
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setContentIntent(PendingIntent.getActivity(
                this,
                10/*notification id*/,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            ))
            .addAction(
                0,
                "Ouvrir",
                /*PendingIntent.getActivity(
                    this,
                    10,
                    *//*Intent(this, NotificationBroadcastReceiver::class.java).apply {
                        this.action = "remove"
                        this.data = Uri.parse("https://www.google.com")
                        notificationManager.cancel("fromNotification",10)
                    }*//*
                    Intent(Intent.ACTION_VIEW,  Uri.parse("https://www.google.com")).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    },
                    PendingIntent.FLAG_MUTABLE
                )*/
                getDismissIntent(10, this)
            )
            .build()

        notificationManager.notify(10, notification)
        //notificationManager.cancel(10)


        //mNotificationManager.notify(NOTIFICATION_ID)

    }

    private fun createNotificationChannel() {
        notificationManager.createNotificationChannel(NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "NOTIFICATION_CHANNEL_NAME",
            NotificationManager.IMPORTANCE_HIGH
        ))
    }

    private fun getDismissIntent(notificationId: Int, context: Context?): PendingIntent? {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("10"/*notification id*/, notificationId)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    }

    private fun createDeleteIntent(): PendingIntent {
        val intent = Intent(this, NotificationBroadcastReceiver::class.java)
        intent.action = "swipe_notif"
        return PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }


    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, arrayOf(POST_NOTIFICATIONS), NOTIF_PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIF_PERMISSION_CODE) {
            val res = if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                "Permission de notification autorisée"
            } else {
                "Permission de notification refusée"
            }
            Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
        }
    }

}
