package fr.pcmprojet2022.learndico.notification

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.SortedList
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.adapter.SearchRecycleAdapter
import fr.pcmprojet2022.learndico.data.entites.Words
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel


class BroadcastReceiversDownload : BroadcastReceiver() {

    /**
    * Gestion des téléchargements
    * */

    companion object{
        private var cpt = 0
        private val mapIdToUrl: MutableMap<Long, WordToUpdate> = mutableMapOf()
    }

    class WordToUpdate(val word : Words, val daoViewModel: DaoViewModel)

    override fun onReceive(context: Context?, intent: Intent?) {

        val downloadID = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)?:-1

        if (downloadID in mapIdToUrl.keys){
            Toast.makeText(context, R.string.dowlaodEnd, Toast.LENGTH_LONG).show()
            val wordToUpdate = mapIdToUrl[downloadID]
            wordToUpdate!!.daoViewModel.addFileName(wordToUpdate.word)

        }else Log.wtf("Receiver", "L'application n'est pas concernée par ce téléchargment")
    }

    fun download(
        sortedList: SortedList<Words>,
        context: Context,
        holder: SearchRecycleAdapter.VH,
        position: Int,
        daoViewModel: DaoViewModel
    ) {

        val downloadManager =
            holder.binding.materialCardView.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri = Uri.parse(sortedList[position].url)

        val fileName = "LearnDico"+(cpt++)+".html"

        val request = DownloadManager.Request(uri)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE/*_NOTIFY_COMPLETED*/)
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS.toString(),
                fileName
            )

        val idDownload : Long = downloadManager.enqueue(request)

        holder.wordObj.fileName = fileName

        mapIdToUrl[idDownload] = WordToUpdate(holder.wordObj, daoViewModel)

    }

}