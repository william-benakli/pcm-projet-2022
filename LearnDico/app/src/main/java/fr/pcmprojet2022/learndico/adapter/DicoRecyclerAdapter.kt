package fr.pcmprojet2022.learndico.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Words
import fr.pcmprojet2022.learndico.databinding.ItemDicoBinding
import fr.pcmprojet2022.learndico.databinding.ItemWordBinding
import fr.pcmprojet2022.learndico.fragment.ListFragmentDirections
import fr.pcmprojet2022.learndico.sharedviewmodel.SharedViewModel

class DicoRecyclerAdapter (private val dico: MutableList<Dico>) : RecyclerView.Adapter<DicoRecyclerAdapter.VH>() {

    class VH(val binding: ItemDicoBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var dictionnaire: Dico
    }

    private var checked_dico_id: Int = 0;
    private var list_id: MutableList<Int> = mutableListOf<Int>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemDicoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = VH(binding)

        for(item in dico) list_id.add(item.key)

        val card : CardView = binding.cardView

        card.setOnClickListener { item ->
            checked_dico_id = Integer.parseInt(binding.idDico.text.toString())
            Log.e("tag click", checked_dico_id.toString())
            if(Integer.parseInt(holder.binding.idDico.text.toString()) == checked_dico_id) card.setBackgroundColor(Color.GRAY);
            else card.setBackgroundColor(Color.WHITE);
        };
        //if(checked_dico)

        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.dictionnaire = dico[position]

        val card : CardView = holder.binding.cardView

        with(holder.binding){
            dico.text = holder.dictionnaire.nom
            srcTr.text = holder.dictionnaire.nom
            destTr.text = holder.dictionnaire.nom
            idDico.text = holder.dictionnaire.key.toString()
            Log.e("tag", holder.dictionnaire.key.toString())
        }
        if(Integer.parseInt(holder.binding.idDico.text.toString()) == checked_dico_id) card.setBackgroundColor(Color.GRAY);
        else card.setBackgroundColor(Color.WHITE);

    }


    override fun getItemCount(): Int = dico.size

}