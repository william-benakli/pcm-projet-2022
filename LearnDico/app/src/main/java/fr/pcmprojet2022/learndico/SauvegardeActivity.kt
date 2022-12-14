package fr.pcmprojet2022.learndico

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.data.entites.Words
import fr.pcmprojet2022.learndico.databinding.ActivitySaveBinding


class SauvegardeActivity : AppCompatActivity() {

    /**
     * Class Sauvegardeactivity est l'activité ou l'utilisateur peut ajouter un mot
     * Elle n'est accessible qu'en partageant un lien
     */
    private val database by lazy{ LearnDicoBD.getInstanceBD(this);}
    private lateinit var binding: ActivitySaveBinding
    private var url: String? = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.action.equals( "android.intent.action.SEND" ) ){
            url = intent.extras?.getString( "android.intent.extra.TEXT" )
        }else{
            Toast.makeText(this, "Une erreur est survenue, ressayez", Toast.LENGTH_LONG).show();
            finish()
        }
        buttonEventClick()
    }

    private fun buttonEventClick() {
        binding.validerId.setOnClickListener {
          if(argsIsOk()){
              Toast.makeText(this, "Votre mot est ajouté à votre liste ", Toast.LENGTH_LONG).show();
            //TODO: requete qui verifie si le mot existe pas déjà
              with(binding){
                  database.getRequestDao().insertMot(Words(saveWordOrigineId.text.toString(), wordTradId.text.toString(), saveLangueSrcId.text.toString(), saveLangueDstId.text.toString(), "", "", url.toString()))
              }
              startActivity(Intent(this, MainActivity::class.java));
          }else{
              Toast.makeText(this, "Les champs saisies sont invalides.", Toast.LENGTH_SHORT).show();
          }
        };
    }

    private fun argsIsOk(): Boolean {
        return (binding.saveLangueSrcId.text.toString().isNotEmpty()&&
                binding.saveLangueDstId.text.toString().isNotEmpty() &&
                binding.wordTradId.text.toString().isNotEmpty() &&
                binding.saveWordOrigineId.text.toString().isNotEmpty())
    }


}