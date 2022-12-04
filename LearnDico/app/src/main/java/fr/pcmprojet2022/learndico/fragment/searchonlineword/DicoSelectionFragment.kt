package fr.pcmprojet2022.learndico.fragment.searchonlineword

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.adapter.DicoRecyclerAdapter
import fr.pcmprojet2022.learndico.databinding.FragmentDicoSelectionBinding
import fr.pcmprojet2022.learndico.databinding.FragmentSearchBinding
import fr.pcmprojet2022.learndico.fragment.ListFragmentDirections
import fr.pcmprojet2022.learndico.sharedviewmodel.SharedViewModel

class DicoSelectionFragment : Fragment(R.layout.fragment_dico_selection) {


    private lateinit var binding: FragmentDicoSelectionBinding
    private lateinit var dicoAdapter: DicoRecyclerAdapter

    private val sharedViewModel by lazy { ViewModelProvider(this)[SharedViewModel::class.java] }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDicoSelectionBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        sharedViewModel.loadAllDico()
        //sharedViewModel.insertDico()

        sharedViewModel.allDicoBD.observe(viewLifecycleOwner) {
          //  if(it.toMutableList().isNotEmpty()) {
                dicoAdapter = DicoRecyclerAdapter(it.toMutableList())
                binding.recyclerView.adapter = dicoAdapter
                Log.d(null, "Chargement des dictionnaires")
            //}
        }


        binding.valide.setOnClickListener {
            val direction = DicoSelectionFragmentDirections.actionDicoSelectionFragmentToWordSelectionFragment()
            findNavController().navigate(direction)
        }


    }

}