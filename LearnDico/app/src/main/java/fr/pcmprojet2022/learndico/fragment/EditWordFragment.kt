package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import fr.pcmprojet2022.learndico.databinding.FragmentEditWordBinding

class EditWordFragment : Fragment(R.layout.fragment_edit_word) {

    lateinit var binding: FragmentEditWordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentEditWordBinding.bind(view)

    }

}