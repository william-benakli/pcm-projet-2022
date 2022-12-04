package fr.pcmprojet2022.learndico.fragment.searchonlineword

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.databinding.FragmentWordSelectionBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.SharedViewModel

class WordSelectionFragment: Fragment(R.layout.fragment_word_selection) {

    lateinit var binding: FragmentWordSelectionBinding
    private val sharedViewModel by lazy { ViewModelProvider(this)[SharedViewModel::class.java] }
    private lateinit var dicoUse : Dico

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWordSelectionBinding.bind(view)
        dicoUse = sharedViewModel.getDico(sharedViewModel.getDicoStats());

        if(sharedViewModel.getDicoStats() == 0){ // Google FireFox etc...
            with(binding){
                dest.visibility  = View.VISIBLE
                src.visibility = View.VISIBLE
            }

        }else{ // les autres dicos
            with(binding){
                binding.dest.visibility  = View.INVISIBLE;
                binding.src.visibility = View.INVISIBLE;
                src.text = ""
                dest.text = ""
                Site.text = ""
            }

        }


        binding.button2.setOnClickListener {

            activity?.let{
                //val intent = Intent (it, MainActivity::class.java)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse( "https://www.google.com/")
                it.startActivity(intent)
            }
        }
    }

}