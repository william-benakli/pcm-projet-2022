package fr.pcmprojet2022.learndico.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import fr.pcmprojet2022.learndico.MainActivity
import fr.pcmprojet2022.learndico.databinding.FragmentEditWordBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel
import fr.pcmprojet2022.learndico.sharedviewmodel.ModifiedWordViewModel

class EditWordFragment : Fragment(R.layout.fragment_edit_word) {

    /**
     * Modifier les descriptions d'un mot
     */

    private lateinit var binding: FragmentEditWordBinding
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }
    private val modifiedWordViewModel : ModifiedWordViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditWordBinding.bind(view)

        with(binding) {
            val word = modifiedWordViewModel.getWord()
            if (word != null) {
                textView.text = word.wordOrigin
                textView2.text = word.wordTranslate

                descSrc.setText(
                    modifiedWordViewModel.getWord()?.wordSignification ?: (R.string.noDescription.toString())
                )
                descTrad.setText(
                    modifiedWordViewModel.getWord()?.translationSignification
                        ?: (R.string.noDescription.toString())
                )
                valider.setOnClickListener {
                    if (descSrc.text!!.isNotEmpty() && descTrad.text!!.isNotEmpty()) {
                        Toast.makeText(context, R.string.motAJour, Toast.LENGTH_LONG).show()
                        word.translationSignification = descTrad.text.toString()
                        word.wordSignification = descSrc.text.toString()
                        daoViewModel.updateWord(word)
                        startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Toast.makeText(context, R.string.invalideChamps, Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(context, R.string.erreur_survenue, Toast.LENGTH_LONG).show()
                startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }

}