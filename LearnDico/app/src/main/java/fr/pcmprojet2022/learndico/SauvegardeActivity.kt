package fr.pcmprojet2022.learndico

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.data.entites.Words
import fr.pcmprojet2022.learndico.databinding.ActivitySaveBinding
import java.io.IOException
import kotlin.concurrent.thread


class SauvegardeActivity : AppCompatActivity() {

    /**
     * Class Sauvegardeactivity est l'activité ou l'utilisateur peut ajouter un mot
     * Elle n'est accessible qu'en partageant un lien
     */
    private val database by lazy{ LearnDicoBD.getInstanceBD(this);}
    private val dicoExist: MutableList<Dico> = mutableListOf<Dico>()
    private val wordsExist: MutableList<Words> = mutableListOf<Words>()

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
              with(binding) {

                  var descriptionOrigine = descriptionTradId.text.toString().replace(" ", "")
                  var descriptionTrad = binding.descriptionOrgineId.text.toString().replace(" ", "")

                  if (descriptionOrigine.isBlank()) descriptionOrigine = "Aucune description"
                  if (descriptionTrad.isBlank()) descriptionTrad = "Aucune description"

                  var mot = Words(
                      binding.saveWordOrigineId.text.toString(),
                      binding.wordTradId.text.toString().replace(" ", ""),
                      binding.saveLangueSrcId.text.toString().replace(" ", ""),
                      binding.saveLangueDstId.text.toString().replace(" ", ""),
                      descriptionOrigine,
                      descriptionTrad,
                      url.toString().replace(" ", "")
                  )
                  addWord(mot)
                  /* Traitement de l'url des dicos et du nom du dictionnaire */
                  val nomDico =
                      url.toString().lowercase().replace("https://www.", "").split(".")[0]
                          .replaceFirstChar { c -> c.uppercase() }
                  val urlDico = url.toString().lowercase()
                      .replace(binding.saveWordOrigineId.text.toString(), "%mot_origine%")
                      .replace(binding.wordTradId.text.toString(), "%mot_trad%")
                  addDictionnaire(urlDico, nomDico);
              }
              Toast.makeText(this, R.string.nouveauMotToList, Toast.LENGTH_LONG).show();
              cleanEntry();
              startActivity(Intent(this, MainActivity::class.java))
              finish()
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

    private fun cleanEntry() {
        binding.saveLangueSrcId.setText("")
        binding.saveLangueDstId.setText("")
        binding.wordTradId.setText("")
        binding.saveWordOrigineId.setText("")
    }

    private fun addDictionnaire(urlDico: String, nomDico: String){
        thread {
            (database.getRequestDao().insertDictionnaire(
                Dico(
                    nomDico,
                    urlDico,
                    binding.saveLangueSrcId.text.toString().lowercase()
                        .replace(" ", ""),
                    binding.saveLangueDstId.text.toString().lowercase()
                        .replace(" ", "")
                )
            ))
        }
    }

    private fun addWord(words: Words) {
        thread {
            thread { database.getRequestDao().insertMot(words)}
        }
    }
}