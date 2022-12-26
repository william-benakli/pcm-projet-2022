package fr.pcmprojet2022.learndico.fragment.searchonlineword

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
import fr.pcmprojet2022.learndico.adapter.DicoRecyclerAdapter
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.databinding.FragmentDicoSelectionBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel
import fr.pcmprojet2022.learndico.sharedviewmodel.SearchOnlineViewModel

class DicoSelectionFragment : Fragment(R.layout.fragment_dico_selection) {

    /**
     * Cette classe est le fragment DicoSelectionOnlineFragment qui
     * permet à l'utilisateur de séléctionner un dictionnaire avant sa recherche.
     */
    private lateinit var binding: FragmentDicoSelectionBinding
    private lateinit var dicoAdapter: DicoRecyclerAdapter

    /*Les view models permet de transferer des informations entre fragment */
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }
    private val searchSharedViewModel: SearchOnlineViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDicoSelectionBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        daoViewModel.loadAllDico()

        daoViewModel.getAllDicoBD().observe(viewLifecycleOwner) {
            val shared = activity?.getSharedPreferences("params_learn_dico", Context.MODE_PRIVATE)
            val list = it.toMutableList()
            var urlBrowser = shared?.getString("urlBrowser", "https://www.google.com/search?q=%mot_origine%").toString()
            urlBrowser += "+%langue_origine%+en+%langue_trad%+dictionnaire"
            urlBrowser.replace("exemple", "%mot_origine%")
            list.add(0, Dico("Moteur de recherche favoris", urlBrowser, "", ""))
            dicoAdapter = DicoRecyclerAdapter(list)
            binding.recyclerView.adapter = dicoAdapter
        }

        buttonEventClick()
    }

    private fun buttonEventClick() {
        binding.valide.setOnClickListener {
            if(dicoAdapter.isSelected()){
                /*Dans l'evenement recuperation du dictionnaire selectionné dans le fragment de recherche */
                dicoAdapter.setSelected(false)
                searchSharedViewModel.setSelectedDico(dicoAdapter.getSelectedDico())
                if((dicoAdapter.getSelectedDico()?.nom ?: "Moteur de recherche favoris") == "Moteur de recherche favoris"){
                    val direction = DicoSelectionFragmentDirections.actionDicoSelectionFragmentToLanguagesSelectionFragment()
                    findNavController().navigate(direction)
                }else{
                    val direction = DicoSelectionFragmentDirections.actionDicoSelectionFragmentToWordSelectionFragment()
                    findNavController().navigate(direction)
                }
            }else{
                val toast = Toast.makeText(context, "Aucun dictionnaire selectionné, ressayez !", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

}