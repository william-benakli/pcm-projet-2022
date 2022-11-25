package fr.pcmprojet2022.learndico

import android.os.Bundle
import android.app.Dialog
import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import fr.pcmprojet2022.learndico.databinding.FragmentDialogBinding


class SelectLangDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FragmentDialogBinding.inflate(LayoutInflater.from(context))

        //TODO: remove
        createChip("str", binding.chipGroupSource)
        createChip("str", binding.chipGroupDestination)

        return AlertDialog.Builder(requireContext())
            .setMessage("Veuillez sélectionner les langues")
            .setView(binding.root)
            .setPositiveButton("Ok") { _,_ ->

            }
            .create()

    }

    //TODO: create chip in listFragment
    private fun createChip(name: String, group: ChipGroup) {
        val chip = Chip(context)
        chip.text = name
        chip.isCheckable = true
        chip.isCloseIconVisible = false
        chip.isChecked = true

        chip.setOnCheckedChangeListener { _, isChecked ->
            chip.isCloseIconVisible = !isChecked
        }

        //TODO: check moyen de comunication

        group.addView(chip)
    }

    companion object {
        const val TAG = "SelectLanguageDialog"
    }

}