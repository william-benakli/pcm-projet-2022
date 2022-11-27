package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import fr.pcmprojet2022.learndico.databinding.FragmentAddDicoBinding

class AddDicoFragment: Fragment(R.layout.fragment_add_dico) {

    lateinit var binding: FragmentAddDicoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentAddDicoBinding.bind(view)

    }

}