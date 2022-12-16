package fr.pcmprojet2022.learndico.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.databinding.DialogAddLanguageAlertDialogBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel

class AddLanguageAlertDialog() : DialogFragment() {

    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }
    private lateinit var binding: DialogAddLanguageAlertDialogBinding;

    /**
     * Cette classe est un dialogFragment elle permet l'ajout d'une nouvelle langue dans
     * la base de donnée.
     *
     */

    @SuppressLint("InflateParams", "UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            binding = DialogAddLanguageAlertDialogBinding.inflate(LayoutInflater.from(context))

            builder.setView(inflater.inflate(R.layout.dialog_add_language_alert_dialog, null))
                .setPositiveButton(R.string.ajouter) { dialog, _ ->
                    if (binding.langueEditTextDialog.text.toString().isEmpty()) {
                        Toast.makeText(context, R.string.incorrectChamps, Toast.LENGTH_SHORT).show()
                    } else {
                        daoViewModel.loadLanguages(binding.langueEditTextDialog.text.toString())
                        val listLanguagesExist = daoViewModel.getLanguesSelected()
                        if (listLanguagesExist.size == 0) {
                            daoViewModel.insertLangues(Langues(binding.langueEditTextDialog.text.toString()))
                            Toast.makeText(
                                context,
                                R.string.nouvelleLangeuAjoute,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else Toast.makeText(
                            context,
                            R.string.langueExisteDeja,
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.cancel()
                    }
                }
                .setNegativeButton(R.string.annuler) { dialog, id -> dialog.cancel() }
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Erreur dialogFragment activité ne peut être null")
    }
}