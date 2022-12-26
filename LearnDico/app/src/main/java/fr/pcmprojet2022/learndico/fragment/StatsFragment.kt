package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import fr.pcmprojet2022.learndico.databinding.FragmentStatsBinding

class StatsFragment : Fragment(R.layout.fragment_stats) {

    lateinit var binding: FragmentStatsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStatsBinding.bind(view)
    }

}