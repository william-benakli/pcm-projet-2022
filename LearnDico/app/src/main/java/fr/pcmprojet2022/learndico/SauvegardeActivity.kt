package fr.pcmprojet2022.learndico

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.data.entites.Dico
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
            Toast.makeText(this, R.string.erreur_survenue, Toast.LENGTH_LONG).show();
            finish()
        }
        buttonEventClick()
    }

    private fun buttonEventClick(){
        binding.validerId.setOnClickListener {
            ajouterMot()
        }
    }
    private fun ajouterMot() {
          if(argsIsOk()){
              Toast.makeText(this, R.string.nouveauMotToList, Toast.LENGTH_LONG).show();
              with(binding){
                  database.getRequestDao().insertMot(Words(saveWordOrigineId.text.toString(),
                      wordTradId.text.toString(),
                      saveLangueSrcId.text.toString(),
                      saveLangueDstId.text.toString(),
                      descriptionOrgineId.text.toString(),
                      descriptionTradId.text.toString(),
                      url.toString()))
              }
              /* Traitement de l'url des dicos et du nom du dictionnaire */
              var nomDico = url.toString().replace("https://", "").split(".")[0].toLowerCase();

                  var urlDico = url.toString().toLowerCase().replace(binding.wordTradId.text.toString(), "%mot_trad%")
                      .replace(binding.saveWordOrigineId.text.toString(), "%mot_origine%")
                      .replace(binding.saveLangueSrcId.text.toString().toLowerCase(), "%langue_origine%")
                      .replace(binding.saveLangueDstId.text.toString().toLowerCase(), "%langue_trad%")

              var listeDico = database.getRequestDao().loadDico(urlDico, nomDico) // si le dico n'existe pas on l'ajoute

              if(listeDico.isEmpty()){
                  database.getRequestDao().insertDictionnaire(
                      Dico(nomDico,
                          urlDico,
                          binding.saveLangueSrcId.text.toString().toLowerCase(),
                          binding.saveLangueDstId.text.toString().toLowerCase()))
              }
              startActivity(Intent(this, MainActivity::class.java));
          }else{
              Toast.makeText(this, R.string.invalideChamps, Toast.LENGTH_SHORT).show();
          }
    }

    private fun argsIsOk(): Boolean {
        return (binding.saveLangueSrcId.text.toString().isNotEmpty()&&
                binding.saveLangueDstId.text.toString().isNotEmpty() &&
                binding.wordTradId.text.toString().isNotEmpty() &&
                binding.saveWordOrigineId.text.toString().isNotEmpty())
    }


}