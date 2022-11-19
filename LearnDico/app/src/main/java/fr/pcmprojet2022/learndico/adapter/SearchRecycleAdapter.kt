package fr.pcmprojet2022.learndico.adapter

import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import fr.pcmprojet2022.learndico.data.Mot
import androidx.recyclerview.widget.RecyclerView
import fr.pcmprojet2022.learndico.databinding.ItemWordBinding


class SearchRecycleAdapter(private val words: MutableList<Mot>) :
    RecyclerView.Adapter<SearchRecycleAdapter.VH>() {

    class VH(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var wordObj: Mot
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = VH(binding)

        holder.binding.delete.setOnClickListener {
            Log.wtf("click","TODO: delete")
        }

        holder.binding.edit.setOnClickListener {
            Log.wtf("click","TODO: edit")
        }

        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.wordObj = words[position]

        with(holder.binding){
            word.text = holder.wordObj.word
            translation.text = holder.wordObj.translation
            translationSignification.text = holder.wordObj.translationSignification
            wordSignification.text = holder.wordObj.wordSignification
        }

    }

    override fun getItemCount(): Int = words.size

}