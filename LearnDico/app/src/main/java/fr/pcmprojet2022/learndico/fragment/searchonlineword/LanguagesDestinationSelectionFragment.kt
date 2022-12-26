package fr.pcmprojet2022.learndico.fragment.searchonlineword

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.adapter.LanguagesRecyclerAdapter
import fr.pcmprojet2022.learndico.databinding.FragmentLanguagesBinding
import fr.pcmprojet2022.learndico.dialog.AddLanguageAlertDialog
import fr.pcmprojet2022.learndico.dialog.DialogCallback
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel
import fr.pcmprojet2022.learndico.sharedviewmodel.LanguageViewModel

import com.google.android.material.floatingactionbutton.FloatingActionButton as FAB

class LanguagesDestinationSelectionFragment : Fragment(R.layout.fragment_languages) ,
    DialogCallback {

    /**
     * Cette classe est le fragment LanguagesSourceSelectionFragment qui
     * permet à l'utilisateur de séléctionner la language source avant sa recherche.
     */

    private lateinit var binding: FragmentLanguagesBinding
    private lateinit var langueAdapter: LanguagesRecyclerAdapter

    /*Les view models permet de transferer des informations entre fragment */
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }
    private val searchSharedViewModel: LanguageViewModel by activityViewModels()


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLanguagesBinding.bind(view)
        binding.languagesId.text = "Languages destination"
        binding.recyclerLanguages.layoutManager = LinearLayoutManager(context)
        daoViewModel.loadAllLangues()
        daoViewModel.getAllLanguagesBD().observe(viewLifecycleOwner) {
            langueAdapter = LanguagesRecyclerAdapter(it.toMutableList())
            binding.recyclerLanguages.adapter = langueAdapter
        }
        buttonEventClick()
        fabEventClick(view)
    }

    private fun fabEventClick(view: View) {
        var dialog = AddLanguageAlertDialog(this)
        binding.fab.setOnClickListener{
            dialog.show(childFragmentManager, "AddLanguageAlertDialog")
        }
    }

    private fun updateRecycler(){
        daoViewModel.loadAllLangues()
        daoViewModel.getAllLanguagesBD().observe(viewLifecycleOwner) {
            langueAdapter = LanguagesRecyclerAdapter(it.toMutableList())
            binding.recyclerLanguages.adapter = langueAdapter
        }
    }

    override fun onPositiveButtonClicked() {
        updateRecycler()
    }

    private fun buttonEventClick() {
        binding.suivantLangueId.setOnClickListener {
            if (langueAdapter.isSelected()) {
                /*Dans l'evenement recuperation du dictionnaire selectionné dans le fragment de recherche */
                langueAdapter.setSelected(false)
                searchSharedViewModel.setSelectedLangueDest(langueAdapter.getSelectedLanguages())
                val direction = LanguagesDestinationSelectionFragmentDirections.actionLanguagesDestinationSelectionFragmentToWordSelectionFragment()
                findNavController().navigate(direction)
            } else {
                val toast = Toast.makeText(
                    context,
                    "Aucune langue selectionnée, ressayez !",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

}