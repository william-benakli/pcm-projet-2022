package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.databinding.FragmentAddWordBinding

class AddWordFragment: Fragment(R.layout.fragment_add_dico) {

    lateinit var binding: FragmentAddWordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentAddWordBinding.bind(view)

    }

}