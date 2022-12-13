package fr.pcmprojet2022.learndico

import android.Manifest.permission.HIDE_OVERLAY_WINDOWS
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ContentInfoCompat.Flags
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.databinding.ActivityMainBinding
import fr.pcmprojet2022.learndico.notification.ServiceNotification


class MainActivity : AppCompatActivity() {

    private val NOTIFICATION_CHANNEL_ID = "10001"
    private val CHANNEL_ID = "channel"
    private val NOTIF_PERMISSION_CODE = 100
    val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    companion object {
        private var cpt=0
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val value = it.data?.getStringExtra("input")
            }
        }

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

        createChannel()
        createNotification()
        requestPermission()

    }

    private fun createChannel(){
        notificationManager.createNotificationChannel(
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "NOTIFICATION_CHANNEL_NAME",
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }

    private fun createNotification(){
        val aIntent = Intent(this, ServiceNotification::class.java)
        aIntent.action = "open_notif"
        aIntent.data = Uri.parse("https://google.com")
        aIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        aIntent.putExtra("notification_id", cpt)
        val actionIntent = PendingIntent.getService(this, 0, aIntent, PendingIntent.FLAG_IMMUTABLE)

        val dIntent = Intent(this, ServiceNotification::class.java)
        dIntent.action = "swipe_notif"
        val deletIntent = PendingIntent.getService(this, 0, dIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDeleteIntent(deletIntent)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .addAction(
                0,
                "Ouvrir",
                actionIntent
            )
            .build()

        notificationManager.notify(cpt, notification)

        cpt++
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(POST_NOTIFICATIONS),
                    NOTIF_PERMISSION_CODE
                )
            }
        }
        if (!Settings.canDrawOverlays(this)) {
            overlayPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIF_PERMISSION_CODE) {
            val res =
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    "Permission de notification autorisée"
                } else {
                    "Permission de notification refusée"
                }
            Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
        }
    }

    private fun overlayPermission() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Vue en premier plan")
            .setMessage("Autorisez-vous l'application à afficher des éléments par-dessus d'autres applications utilisées?")
            .setIcon(R.drawable.ic_round_notification_important_24)
            .setCancelable(false)
            .setNegativeButton("Non") { dialog, which ->
                Toast.makeText(this, "Vous ne pourrez pas ouvrir les notifications si l'application est en arrière plan.", Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Oui") { dialog, which ->
                val intent = Intent(
                    ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                getResult.launch(intent)
            }
            .show()
    }

}
