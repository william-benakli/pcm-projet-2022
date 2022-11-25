package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.databinding.FragmentEditWordBinding

class EditWordFragment : Fragment(R.layout.fragment_edit_word) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditWordBinding.inflate(inflater, container, false).root
    }

}