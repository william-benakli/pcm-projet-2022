package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.databinding.FragmentSearchBinding


class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToEditWordFragment()
            findNavController().navigate(direction)
        }

    }

}