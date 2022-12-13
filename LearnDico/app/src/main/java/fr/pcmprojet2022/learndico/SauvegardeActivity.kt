package fr.pcmprojet2022.learndico

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.databinding.ActivitySaveBinding

class SauvegardeActivity : AppCompatActivity() {

    val database by lazy{ LearnDicoBD.getInstanceBD(this);}
    private lateinit var binding: ActivitySaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if( intent.action.equals( "android.intent.action.SEND" ) ){
            val txt = intent.extras?.getString( "android.intent.extra.TEXT" )
         /*       ...
             si txt différent de null alors txt contient maintenant une réfé
                        rence vers un String
             avec l’adresse Url
             de la page qui s’affichait dans le navigateur
          */
        }
    }
}