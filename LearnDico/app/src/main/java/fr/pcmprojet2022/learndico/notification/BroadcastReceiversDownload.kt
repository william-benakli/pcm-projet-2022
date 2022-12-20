package fr.pcmprojet2022.learndico.notification

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.SortedList
import fr.pcmprojet2022.learndico.adapter.SearchRecycleAdapter
import fr.pcmprojet2022.learndico.data.entites.Words


class BroadcastReceiversDownload : BroadcastReceiver() {

    //TODO: supprimer

    companion object{
        private var cpt = 0
        private val mapIdToUrl: MutableMap<Long, String> = mutableMapOf()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        /*val bundle = intent!!.extras
        if (bundle != null) {
            for (key in bundle.keySet()) {
                Log.e(TAG, key + " : " + if (bundle[key] != null) bundle[key] else "NULL")
            }
        }*/
        /*if (intent!=null){
            Log.wtf("Receiver", intent!!.getLongExtra("extra_download_id", -100L).toString())*//*!!.getIntExtra("id_download", 0).toString()*//*
            val broadcastDownloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.wtf("BRN", broadcastDownloadID.toString())
            if (broadcastDownloadID == 0L){
                Log.wtf("Hi", "ok")
                val rep = if (getStatusDownload(context!!) == DownloadManager.STATUS_SUCCESSFUL){
                    "Téléchargement terminé"
                }else{
                    "Téléchargement en cours"
                }
                Toast.makeText(context, rep, Toast.LENGTH_LONG).show()
            }
        }*/

        val downloadID = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)?:-1

        if (downloadID == -1L)Log.wtf("Receiver","broadcastDownloadID")

        Log.wtf("RECEIVER", intent.toString())

        Log.wtf("RECEIVER", downloadID.toString())

        Log.wtf("RECEIVER", mapIdToUrl.toString())

        if (downloadID in mapIdToUrl.keys){

            Toast.makeText(context, "Téléchargement terminé", Toast.LENGTH_LONG).show()

        }else Log.wtf("Receiver", "L'application n'est pas concernée par ce téléchargment")


    }

    /*private fun getStatusDownload(context: Context) : Int {
        val query = DownloadManager.Query()
        query.setFilterById(0L)
        val dlManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val cursor = dlManager.query(query)

        if (cursor.moveToFirst()){
            val colIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            return cursor.getInt(colIndex)
        }

        return DownloadManager.ERROR_UNKNOWN

    }*/

    fun download(
        sortedList: SortedList<Words>,
        context: Context,
        holder: SearchRecycleAdapter.VH,
        position: Int
    ) {

        val downloadManager =
            holder.binding.materialCardView.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri = Uri.parse(sortedList[position].url)

        //TODO: add extension ?
        val fileName = "LearnDico.html" //"+(cpt++)+"

        val request = DownloadManager.Request(uri)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS.toString(),
                fileName
            )
            /*.setTitle("LearnDico")
            .setDescription("Téléchargment en cours de la page web pour le mot "+sortedList[position].wordOrigin)*/

        val idDownload : Long = downloadManager.enqueue(request)

        mapIdToUrl[idDownload] = fileName

        holder.wordObj.wordOrigin
        holder.wordObj.url

        //TODO: save in bdd idDownload

    }

}