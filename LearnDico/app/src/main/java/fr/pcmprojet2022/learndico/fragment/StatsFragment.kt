package fr.pcmprojet2022.learndico.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.pcmprojet2022.learndico.databinding.FragmentStatsBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel

class StatsFragment : Fragment(R.layout.fragment_stats) {

    /**
     * Classe StatsFragment est la statistique et jeu de l'application.
     */

    private lateinit var binding: FragmentStatsBinding
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStatsBinding.bind(view)
        daoViewModel.swipUpdate()
        val nombreMot = daoViewModel.getSwipeNumber()
        binding.statsMotId.text = "$nombreMot Mots"
    }

}