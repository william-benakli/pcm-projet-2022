package fr.pcmprojet2022.learndico

import androidx.lifecycle.Lifecycle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.pcmprojet2022.learndico.fragment.StatsFragment
import fr.pcmprojet2022.learndico.fragment.SearchFragment
import fr.pcmprojet2022.learndico.fragment.PracticeFragment
import fr.pcmprojet2022.learndico.fragment.SettingsFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SettingsFragment()
            }
            1 -> {
                SearchFragment()
            }
            2 -> {
                PracticeFragment()
            }
            else -> {
                StatsFragment()
            }
        }
    }

}