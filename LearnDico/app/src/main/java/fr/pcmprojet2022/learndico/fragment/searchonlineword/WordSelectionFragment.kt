package fr.pcmprojet2022.learndico.fragment.searchonlineword

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.databinding.FragmentWordSelectionBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.LanguageViewModel
import fr.pcmprojet2022.learndico.sharedviewmodel.SearchOnlineViewModel

class WordSelectionFragment: Fragment(R.layout.fragment_word_selection) {

    /**
     *   Cette classe est le fragment WordSelectionFragment qui
     *   permet à l'utilisateur de choisir un mot de recherche parmit un dictionnaire séléctionné dans le
     *   fragment précédent.
     */

    private lateinit var binding: FragmentWordSelectionBinding
    private val searchSharedViewModel: SearchOnlineViewModel by activityViewModels()
    private val languagesSharedViewModel: LanguageViewModel by activityViewModels()
    private var searchWord: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWordSelectionBinding.bind(view)
        loadValueFromBundle(savedInstanceState)
        laodBundleValue()
        val dicoUse = searchSharedViewModel.getSelectedDico()
        loadDictionnaireToTextInput(dicoUse)
        buttonEventClick(dicoUse)
    }

    /**
      * Cette fonction pré-remplie les champs lorsque le mot est déjà connu dictionnaire est déjà
      * connu par l'utilisateur et préremplie les champs.
    */

    private fun loadDictionnaireToTextInput(dicoUse: Dico?) {
        if(dicoUse != null) {
            if (dicoUse.nom == "Moteur de recherche favoris") {
                with(binding) {
                    val srclangue = languagesSharedViewModel.getSelectedLangueSrc()
                    val dstlangue = languagesSharedViewModel.getSelectedLangueDest()
                    dictionnaire.editText!!.setText(dicoUse.nom)
                    if (srclangue != null) {
                        langueSrc.setText(srclangue.languages)
                    }
                    if (dstlangue != null) {
                        langueDest.setText(dstlangue.languages)
                    }
                }
            }else{
                with(binding){
                    dictionnaire.editText!!.setText(dicoUse.nom)
                    langueSrc.setText(dicoUse.src)
                    langueDest.setText(dicoUse.dst)
                }
            }
        }else{
            val toast = Toast.makeText(context, R.string.impossibleDicoLoad, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    /**
       Cette fonction gere les événements liés au clique du boutton de recherche.
     */

    private fun buttonEventClick(dicoUse: Dico?) {
        binding.recherche.setOnClickListener {
            if(!(binding.mot.text?.isEmpty())!!){
                        val intent = Intent(Intent.ACTION_VIEW)
                        val urlDico = dicoUse?.url
                            ?.replace("%mot_origine%", binding.mot.text.toString().replace(" ", "+"))
                            ?.replace("%langue_origine%", binding.langueSrc.text.toString())
                            ?.replace("%langue_trad%",binding.langueDest.text.toString())
                            ?.replace(" ", "")
                            intent.data = Uri.parse(urlDico)
                            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK
                if (urlDico != null) {
                    Log.w("d<##########ico use", urlDico)
                }
                startActivity(intent)
            }else{
                Toast.makeText(context, R.string.invalideChamps, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun loadValueFromBundle(savedInstanceState: Bundle?) {
        searchWord = savedInstanceState?.getString("searchWord")?: ""
    }
    private fun laodBundleValue() {
        with(binding) {
            mot.setText(searchWord)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchWord", searchWord)
    }

}