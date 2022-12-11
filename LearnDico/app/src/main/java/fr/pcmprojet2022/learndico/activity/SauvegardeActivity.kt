package fr.pcmprojet2022.learndico.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import fr.pcmprojet2022.learndico.R

class SauvegardeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_save)

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