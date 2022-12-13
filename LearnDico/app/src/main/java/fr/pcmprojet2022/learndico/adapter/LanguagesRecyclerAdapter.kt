package fr.pcmprojet2022.learndico.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.databinding.ItemLanguagesBinding

class LanguagesRecyclerAdapter (private val list_langues: MutableList<Langues>) : RecyclerView.Adapter<LanguagesRecyclerAdapter.VH>() {

    /**
     * Cette class represente la selection des langues disponibles sur l'application
     * sout forme de recyclerAdapater
     *
     */
    class VH(val binding: ItemLanguagesBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var language: Langues
    }

    private var checkedLanguages : Langues? = null;
    private var isSelectedLanguages: Boolean = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLanguagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = VH(binding)
        val card : CardView = binding.cardViewLanguage
        card.setOnClickListener {
            card.setBackgroundColor(Color.WHITE);
            checkedLanguages = list_langues[holder.absoluteAdapterPosition];
            if(list_langues[holder.absoluteAdapterPosition] == checkedLanguages)card.setBackgroundColor(
                Color.GRAY)
            isSelectedLanguages = true;
            updateReclycler();
        }
        return holder
    }

    override fun onBindViewHolder(holder: LanguagesRecyclerAdapter.VH, position: Int) {
        holder.language = list_langues[position]
        val card : CardView = holder.binding.cardViewLanguage
        holder.binding.languagesId.text = holder.language.languages
        if(list_langues[holder.absoluteAdapterPosition] == checkedLanguages) card.setBackgroundColor(Color.GRAY);
        else card.setBackgroundColor(Color.WHITE);
    }

    override fun getItemCount(): Int { return list_langues.size }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateReclycler() {
        notifyDataSetChanged();
    }

    /*Cette fonction permet de s'assurer que l'utilisateur a bien selectionné un dictionnaire avant de continuer */
    fun isSelected(): Boolean{
        return isSelectedLanguages;
    }

    fun setSelected(bool: Boolean){
        isSelectedLanguages = bool;
    }

    fun getSelectedLanguages(): Langues? { return checkedLanguages }
}