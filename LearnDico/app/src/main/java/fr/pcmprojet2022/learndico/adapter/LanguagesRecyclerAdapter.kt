package fr.pcmprojet2022.learndico.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.databinding.ItemLanguagesBinding

class LanguagesRecyclerAdapter (private val list_langues: MutableList<Langues>) : RecyclerView.Adapter<LanguagesRecyclerAdapter.VH>() {

    val callback = object : SortedList.Callback<Langues>() {
        override fun compare(o1: Langues?, o2: Langues?): Int =
            o1!!.languages.compareTo(o2!!.languages)

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeInserted(position, itemCount)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

        override fun onChanged(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun areContentsTheSame(oldItem: Langues?, newItem: Langues?): Boolean =
            (oldItem == null || newItem == null) || newItem == oldItem

        override fun areItemsTheSame(item1: Langues?, item2: Langues?): Boolean =
            item1 === item2

    }

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
    //private var edt_list_langues = (mutableListOf<Langues>())

    private val sortedList = SortedList(Langues::class.java, callback)

    init {
        sortedList.addAll(list_langues)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLanguagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = VH(binding)
        val card : CardView = binding.cardViewLanguage
        card.setOnClickListener {

            val typedValue = TypedValue()
            parent.context.theme.resolveAttribute(R.attr.colorBackground, typedValue, true)
            card.setCardBackgroundColor(typedValue.data)
            checkedLanguages = sortedList[holder.absoluteAdapterPosition];

            if(sortedList[holder.absoluteAdapterPosition] == checkedLanguages){
                parent.context.theme.resolveAttribute(R.attr.colorButtonNormal, typedValue, true)
                card.setCardBackgroundColor(typedValue.data)
            }

            isSelectedLanguages = true;
            updateReclycler();
        }
        return holder
    }

    override fun onBindViewHolder(holder: LanguagesRecyclerAdapter.VH, position: Int) {
        holder.language = sortedList[position]
        val card : CardView = holder.binding.cardViewLanguage
        holder.binding.languagesId.text = holder.language.languages

        val typedValue = TypedValue()
        val contextTheme = holder.binding.cardViewLanguage.context.theme
        if(sortedList[holder.absoluteAdapterPosition] == checkedLanguages){
            contextTheme.resolveAttribute(R.attr.colorButtonNormal, typedValue, true)
            card.setCardBackgroundColor(typedValue.data)
        }else {
            contextTheme.resolveAttribute(R.attr.colorBackground, typedValue, true)
            card.setCardBackgroundColor(typedValue.data)
        }
    }

    override fun getItemCount(): Int { return sortedList.size() }

    @SuppressLint("NotifyDataSetChanged")
    fun updateReclycler() {
        this.notifyDataSetChanged();
        sortedList.clear();
        sortedList.addAll(list_langues);
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