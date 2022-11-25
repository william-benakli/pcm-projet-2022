package fr.pcmprojet2022.learndico

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import fr.pcmprojet2022.learndico.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = (navHostFragment as NavHostFragment).findNavController()
        binding.menuNavigation.setupWithNavController(navController);

    }

}