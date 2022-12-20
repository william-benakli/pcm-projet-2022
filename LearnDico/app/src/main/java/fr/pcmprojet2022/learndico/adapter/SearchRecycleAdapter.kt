package fr.pcmprojet2022.learndico.adapter

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedList.Callback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.pcmprojet2022.learndico.BuildConfig
import fr.pcmprojet2022.learndico.data.entites.Words
import fr.pcmprojet2022.learndico.databinding.ItemWordBinding
import fr.pcmprojet2022.learndico.fragment.ListFragmentDirections
import fr.pcmprojet2022.learndico.notification.BroadcastReceiversDownload
import java.io.File


class SearchRecycleAdapter(private val words: MutableList<Words>, private val context: Context) : RecyclerView.Adapter<SearchRecycleAdapter.VH>() {

    val callback = object : Callback<Words>() {
        override fun compare(o1: Words?, o2: Words?): Int =
            o1!!.wordTranslate.compareTo(o2!!.wordTranslate)

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeInserted(position, itemCount)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

        override fun onChanged(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun areContentsTheSame(oldItem: Words?, newItem: Words?): Boolean =
            (oldItem == null || newItem == null) || newItem == oldItem

        override fun areItemsTheSame(item1: Words?, item2: Words?): Boolean =
            item1 === item2

    }

    private val broadcastReceiversDownload = BroadcastReceiversDownload()

    private val sortedList = SortedList(Words::class.java, callback)

    init {
        sortedList.addAll(words)
    }

    class VH(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var wordObj: Words
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = VH(binding)

        holder.binding.delete.setOnClickListener {
            Log.wtf("click","TODO: delete")
            MaterialAlertDialogBuilder(context)
                .setTitle("Supprimer le mot")
                .setMessage("Êtes vous sûr de bien vouloir supprimer ce mot?")
                .setNegativeButton("Non") { dialog, which ->
                    //TODO: Respond to negative button press
                }
                .setPositiveButton("Oui") { dialog, which ->
                    //TODO: Respond to positive button press
                }
                .show()
        }

        //sharedViewModel = ViewModelProvider(fragment).get(SharedViewModel::class.java);

        holder.binding.edit.setOnClickListener {
            Log.wtf("click","TODO: edit")
            println(binding.word.text.toString() + " AAAAA");
            //sharedViewModel.saveMot(binding.word.text.toString() + " AAAAA");

            val direction = ListFragmentDirections.actionListFragmentToEditWordFragment()
            parent.findNavController().navigate(direction)
        }

        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.wordObj = sortedList[position]

        with(holder.binding){
            word.text = holder.wordObj.wordOrigin
            translation.text = holder.wordObj.wordTranslate
            translationSignification.text = holder.wordObj.translationSignification
            wordSignification.text = holder.wordObj.wordSignification
        }

        holder.binding.download.setOnClickListener {
            broadcastReceiversDownload.download(sortedList, context, holder, position)
        }

        holder.binding.openDownload.setOnClickListener {
            val intentI = Intent(Intent.ACTION_VIEW)
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "LearnDico.html"
            )
            intentI.data = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )
            intentI.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intentI)
        }

    }

    override fun getItemCount(): Int = sortedList.size()

}