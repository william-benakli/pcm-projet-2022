package fr.pcmprojet2022.learndico

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import fr.pcmprojet2022.learndico.adapter.FragmentAdapter
import fr.pcmprojet2022.learndico.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_round_settings_24)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_round_search_24)
                }
                2 -> {
                    tab.setIcon(R.drawable.ic_round_format_list_bulleted_24)
                }
                3 -> {
                    tab.setIcon(R.drawable.ic_round_data_thresholding_24)
                }
            }
        }.attach()
    }
}