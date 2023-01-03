package fr.pcmprojet2022.learndico.fragment.searchonlineword

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.adapter.LanguagesRecyclerAdapter
import fr.pcmprojet2022.learndico.databinding.FragmentLanguagesBinding
import fr.pcmprojet2022.learndico.dialog.AddLanguageAlertDialog
import fr.pcmprojet2022.learndico.dialog.DialogCallback
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel
import fr.pcmprojet2022.learndico.sharedviewmodel.LanguageViewModel

class LanguagesSourceSelectionFragment : Fragment(R.layout.fragment_languages) , DialogCallback {


    /**
     * Cette classe est le fragment LanguagesSourceSelectionFragment qui
     * permet à l'utilisateur de séléctionner la language source avant sa recherche.
     */

    private lateinit var binding: FragmentLanguagesBinding
    private lateinit var langueAdapter: LanguagesRecyclerAdapter

    /*Les view models permet de transferer des informations entre fragment */
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }
    private val searchSharedViewModel: LanguageViewModel by activityViewModels()
    private val dialog = AddLanguageAlertDialog(this)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLanguagesBinding.bind(view)
        binding.languagesId.text = "Langues source"
        binding.recyclerLanguages.layoutManager = LinearLayoutManager(context)
        daoViewModel.loadAllLangues()
        updateRecycler()
        buttonEventClick()
        fabEventClick()
    }


    private fun fabEventClick() {
        binding.fab.setOnClickListener{
            dialog.show(childFragmentManager, "AddLanguageAlertDialog")
        }
    }

    private fun updateRecycler(){
        daoViewModel.getAllLanguagesBD().removeObservers(this@LanguagesSourceSelectionFragment)
        daoViewModel.loadAllLangues()
        daoViewModel.getAllLanguagesBD().observe(viewLifecycleOwner) {
            langueAdapter = LanguagesRecyclerAdapter(it.toMutableList())
            binding.recyclerLanguages.adapter = langueAdapter
        }
    }
    override fun onPositiveButtonClicked() {
        updateRecycler()
    }

    override fun onResume() {
        super.onResume()
        updateRecycler()
    }


    private fun buttonEventClick() {
        binding.suivantLangueId.setOnClickListener {
            if(langueAdapter.isSelected()){
                /*Dans l'evenement recuperation du dictionnaire selectionné dans le fragment de recherche */
                langueAdapter.setSelected(false)
                searchSharedViewModel.setSelectedLangueSrc(langueAdapter.getSelectedLanguages())
                val direction = LanguagesSourceSelectionFragmentDirections.actionLanguagesSelectionFragmentToLanguagesDestinationSelectionFragment()
                findNavController().navigate(direction)
            }else{
                Toast.makeText(context,
                    R.string.aucune_langue,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}