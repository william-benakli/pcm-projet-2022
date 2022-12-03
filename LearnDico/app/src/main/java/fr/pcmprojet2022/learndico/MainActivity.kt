package fr.pcmprojet2022.learndico

import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import androidx.appcompat.app.AppCompatActivity
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.fragment.findNavController
import fr.pcmprojet2022.learndico.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val database by lazy{LearnDicoBD.getInstanceBD(this);}
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NavigationUI: automatisation de la gestion des multi backstacks
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = (navHostFragment as NavHostFragment).findNavController()
        binding.menuNavigation.setupWithNavController(navController)

        //permets de conserver plusieurs piles tout en navigant vers le bon fragment
        binding.menuNavigation.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }

    }
  

}