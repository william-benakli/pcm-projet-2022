package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import fr.pcmprojet2022.learndico.databinding.FragmentEditWordBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.SharedViewModel

class EditWordFragment : Fragment(R.layout.fragment_edit_word) {

    lateinit var binding: FragmentEditWordBinding

    private val sharedViewModel by lazy { ViewModelProvider(this)[SharedViewModel::class.java] }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentEditWordBinding.bind(view)

       // sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java];

        sharedViewModel.motLiveData.observe(viewLifecycleOwner) { mot ->
            println("$mot EEEE5E");
            binding.textView.text = mot.toString();
        };

    }

}