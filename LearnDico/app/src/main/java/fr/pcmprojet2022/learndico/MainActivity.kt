package fr.pcmprojet2022.learndico

import java.util.*
import android.app.*
import android.net.Uri
import android.util.Log
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.os.SystemClock
import android.content.Context
import android.provider.Settings
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.navigation.ui.NavigationUI
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.fragment.findNavController
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.activity.result.contract.ActivityResultContracts
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.databinding.ActivityMainBinding
import fr.pcmprojet2022.learndico.notification.ServiceNotification
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.notification.BroadcastReceiversDownload

class MainActivity : AppCompatActivity() {

    private val NOTIF_PERMISSION_CODE = 100
    private val NOTIFICATION_CHANNEL_ID = "10001"

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val value = it.data?.getStringExtra("input")
            }
        }

    val broadcastReceiversDownload = BroadcastReceiversDownload()

    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    //TODO : Clean navgraph

    val database by lazy{ LearnDicoBD.getInstanceBD(this);}
    
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

        registerReceiver(
            BroadcastReceiversDownload(),
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )


        requestPermission()
        createChannel()
        createJob()



    }

    /*private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val broadcastDownloadID = intent!!.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (broadcastDownloadID == id){
                val rep = if (getStatusDownload() == DownloadManager.STATUS_SUCCESSFUL){
                    "Téléchargement terminé"
                }else{
                    "Téléchargement en cours"
                }
                Toast.makeText(context, rep, Toast.LENGTH_LONG).show()
            }
        }

        private fun getStatusDownload() : Int {
            val query = DownloadManager.Query()
            query.setFilterById(id)
            val dlManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val cursor = dlManager.query(query)

            if (cursor.moveToFirst()){
                val colIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                return cursor.getInt(colIndex)
            }

            return DownloadManager.ERROR_UNKNOWN

        }

    }*/

    private fun createJob() {
        val aIntent = Intent(this, ServiceNotification::class.java)
        aIntent.action = "run_notif"
        aIntent.flags= Intent.FLAG_ACTIVITY_NEW_TASK

        /* récupérer la référence vers AlarmManager */
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val shared = getSharedPreferences("params_learn_dico", Context.MODE_PRIVATE)

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, shared.getInt("timeHour", 12))
            set(Calendar.MINUTE, shared.getInt("timeMin", 10))
            set(Calendar.SECOND, 0)
        }

        Log.wtf("",(calendar.timeInMillis-SystemClock.elapsedRealtime()).toString())

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY /*60000*/,
            PendingIntent.getService(this, 0, aIntent, PendingIntent.FLAG_IMMUTABLE)
        )

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
