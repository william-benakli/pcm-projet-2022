package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.pcmprojet2022.learndico.databinding.FragmentSearchBinding


class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var binding: FragmentSearchBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        binding.rechercher.setOnClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToDicoSelectionFragment()
            findNavController().navigate(direction)
        }

    }

}