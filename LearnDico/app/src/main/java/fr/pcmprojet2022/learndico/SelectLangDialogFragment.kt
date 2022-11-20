package fr.pcmprojet2022.learndico

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import fr.pcmprojet2022.learndico.databinding.FragmentDialogBinding


class SelectLangDialogFragment: DialogFragment() {

    //lateinit var binding:

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = FragmentDialogBinding.inflate(LayoutInflater.from(context))
        val view = binding.root

        val chip = Chip(context)
        chip.text = "ABC"
        chip.isCloseIconVisible = true

        //TODO: check moyen de comunication

        binding.chipGroupSource.addView(chip)

        return AlertDialog.Builder(requireContext())
            .setMessage("Veuillez sélectionner les langues")
            .setView(view)
            .setPositiveButton("Ok") { _,_ ->

            }
            .create()
    }



    companion object {
        const val TAG = "SelectLanguageDialog"
    }

}