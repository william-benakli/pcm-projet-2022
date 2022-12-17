package fr.pcmprojet2022.learndico.adapter

import android.R
import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.databinding.ItemDicoBinding


class DicoRecyclerAdapter (private val list_dico: MutableList<Dico>) : RecyclerView.Adapter<DicoRecyclerAdapter.VH>() {

    class VH(val binding: ItemDicoBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var dictionnaire: Dico
    }

    private var checkedDico : Dico? = null;
    private var isSelectedDico:Boolean = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemDicoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = VH(binding)
        val card : CardView = binding.cardView
        card.setOnClickListener {
            val typedValue = TypedValue()
            parent.context.theme.resolveAttribute(R.attr.colorBackground, typedValue, true)
            card.setCardBackgroundColor(typedValue.data)
            checkedDico = list_dico[holder.absoluteAdapterPosition];
            if(list_dico[holder.absoluteAdapterPosition] == checkedDico){
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
        holder.dictionnaire = list_dico[position]

        //val card : CardView = holder.binding.cardView

        with(holder.binding){
            dico.text = holder.dictionnaire.nom
            srcTr.text = holder.dictionnaire.nom
            destTr.text = holder.dictionnaire.nom
        }
        /*if(list_dico[holder.absoluteAdapterPosition] == checkedDico) card.setBackgroundColor(Color.GRAY);
        else card.setBackgroundColor(Color.WHITE);*/

    }

    override fun getItemCount(): Int = list_dico.size

    fun getSelectedDico(): Dico? { return checkedDico }

    /*Cette fonction permet de s'assurer que l'utilisateur a bien selectionné un dictionnaire avant de continuer */
    fun isSelected(): Boolean{
        return isSelectedDico;
    }

    fun setSelected(bool: Boolean){
        isSelectedDico = bool;
    }
}