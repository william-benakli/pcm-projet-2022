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
              if(wordNotExist(url.toString().replace(" ", ""))) {

                  with(binding) {
                      var descriptionOrigine = descriptionTradId.text.toString().replace(" ", "");
                      var descriptionTrad = descriptionOrgineId.text.toString().replace(" ", "");

                      if (descriptionOrigine.isBlank()) descriptionOrigine = "Aucune description"
                      if (descriptionTrad.isBlank()) descriptionTrad = "Aucune description"

                      var mot = Words(
                          saveWordOrigineId.text.toString(),
                          wordTradId.text.toString().replace(" ", ""),
                          saveLangueSrcId.text.toString().replace(" ", ""),
                          saveLangueDstId.text.toString().replace(" ", ""),
                          descriptionOrigine,
                          descriptionTrad,
                          url.toString().replace(" ", "")
                      )
                      addWord(mot)
                  }
                  
                  /* Traitement de l'url des dicos et du nom du dictionnaire */
                  val nomDico =
                      url.toString().replace("https://www.", "").split(".")[0].toLowerCase()
                          .replaceFirstChar { c -> c.toUpperCase() }
                  val urlDico = url.toString().toLowerCase()
                      .replace(binding.saveWordOrigineId.text.toString(), "%mot_origine%")
                      .replace(binding.wordTradId.text.toString(), "%mot_trad%")

                  if (dicoNotExist(urlDico, nomDico)) addDictionnaire(urlDico, nomDico);
                  
                  Toast.makeText(this, R.string.nouveauMotToList, Toast.LENGTH_LONG).show();
                  startActivity(Intent(this, MainActivity::class.java));
              }else{
                  Toast.makeText(this, R.string.motExistDeja, Toast.LENGTH_SHORT).show();
              }
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

    private fun dicoNotExist(urlDico: String, nomDico: String): Boolean{
        thread{
            dicoExist.clear()
            dicoExist.addAll(database.getRequestDao().loadDico(urlDico, nomDico))
            var el = database.getRequestDao().loadAllDico()
            for(item in el){
                Log.wtf("--------------", item.url + " " + item.nom)
            }
            Log.wtf("sizethread", dicoExist.size.toString())
        }
        Log.wtf("size dicoExist", dicoExist.size.toString())
        Log.wtf("nomDico", nomDico)
        Log.wtf("urlDico", urlDico)

        return dicoExist.isEmpty()
    }

    private fun wordNotExist(url: String): Boolean{
        thread{
            wordsExist.clear()
            wordsExist.addAll(database.getRequestDao().loadWords(url))
        }
        Log.wtf("size wordsExist", wordsExist.size.toString())
        Log.wtf("url", url)

        return wordsExist.isEmpty()
    }

    private fun addDictionnaire(urlDico: String, nomDico: String){
        thread {
            (database.getRequestDao().insertDictionnaire(
                Dico(
                    nomDico,
                    urlDico,
                    binding.saveLangueSrcId.text.toString().toLowerCase()
                        .replace(" ", ""),
                    binding.saveLangueDstId.text.toString().toLowerCase()
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