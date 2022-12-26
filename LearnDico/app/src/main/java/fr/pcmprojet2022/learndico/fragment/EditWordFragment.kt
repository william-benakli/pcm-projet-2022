package fr.pcmprojet2022.learndico.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.pcmprojet2022.learndico.MainActivity
import fr.pcmprojet2022.learndico.databinding.FragmentEditWordBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel

class EditWordFragment : Fragment(R.layout.fragment_edit_word) {

    lateinit var binding: FragmentEditWordBinding

    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentEditWordBinding.bind(view)

        Toast.makeText(context, "Votre mot a été mis à jour.", Toast.LENGTH_LONG).show()
        daoViewModel.updateWord()
        startActivity(Intent(context, MainActivity::class.java))
    }

}