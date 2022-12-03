package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import fr.pcmprojet2022.learndico.R
import fr.pcmprojet2022.learndico.databinding.FragmentSelectLangBinding


class SelectLangFragment: Fragment(R.layout.fragment_select_lang) {

    private lateinit var binding: FragmentSelectLangBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectLangBinding.bind(view)

        createChip("str", binding.chipGroupSource)
        createChip("str", binding.chipGroupDestination)

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

}