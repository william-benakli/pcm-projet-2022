package fr.pcmprojet2022.learndico.adapter

import android.R
import android.annotation.SuppressLint
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.databinding.ItemDicoBinding


class DicoRecyclerAdapter (private val list_dico: MutableList<Dico>) : RecyclerView.Adapter<DicoRecyclerAdapter.VH>() {

    val callback = object : SortedList.Callback<Dico>() {
        override fun compare(o1: Dico?, o2: Dico?): Int =
            o1!!.nom.compareTo(o2!!.nom)

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeInserted(position, itemCount)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

        override fun onChanged(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun areContentsTheSame(oldItem: Dico?, newItem: Dico?): Boolean =
            (oldItem == null || newItem == null) || newItem == oldItem

        override fun areItemsTheSame(item1: Dico?, item2: Dico?): Boolean =
            item1 === item2

    }

    class VH(val binding: ItemDicoBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var dictionnaire: Dico
    }

    private var checkedDico : Dico? = null;
    private var isSelectedDico:Boolean = false;

    private val sortedList = SortedList(Dico::class.java, callback)

    init {
        sortedList.addAll(list_dico)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemDicoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = VH(binding)
        val card : CardView = binding.cardView
        card.setOnClickListener {
            val typedValue = TypedValue()
            parent.context.theme.resolveAttribute(R.attr.colorBackground, typedValue, true)
            card.setCardBackgroundColor(typedValue.data)
            checkedDico = sortedList[holder.absoluteAdapterPosition];
            if(sortedList[holder.absoluteAdapterPosition] == checkedDico){
                parent.context.theme.resolveAttribute(R.attr.colorButtonNormal, typedValue, true)
                card.setCardBackgroundColor(typedValue.data)
            }
            isSelectedDico = true
            updateReclycler()
        }
        return holder
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateReclycler() {
        notifyDataSetChanged();
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.dictionnaire = sortedList[position]

        val card : CardView = holder.binding.cardView

        with(holder.binding){
            dico.text = holder.dictionnaire.nom
            srcTr.text = holder.dictionnaire.nom
            destTr.text = holder.dictionnaire.nom
        }

        val typedValue = TypedValue()
        val contextTheme = holder.binding.cardView.context.theme
        if (sortedList[holder.absoluteAdapterPosition] == checkedDico) {
            contextTheme.resolveAttribute(R.attr.colorButtonNormal, typedValue, true)
            card.setCardBackgroundColor(typedValue.data)
        } else {
            contextTheme.resolveAttribute(R.attr.colorBackground, typedValue, true)
            card.setCardBackgroundColor(typedValue.data)
        }

    }

    override fun getItemCount(): Int = sortedList.size()

    fun getSelectedDico(): Dico? { return checkedDico }

    /*Cette fonction permet de s'assurer que l'utilisateur a bien selectionné un dictionnaire avant de continuer */
    fun isSelected(): Boolean{
        return isSelectedDico;
    }

    fun setSelected(bool: Boolean){
        isSelectedDico = bool;
    }
}