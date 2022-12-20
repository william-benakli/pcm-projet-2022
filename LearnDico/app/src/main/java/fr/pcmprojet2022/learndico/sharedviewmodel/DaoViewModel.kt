package fr.pcmprojet2022.learndico.sharedviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import fr.pcmprojet2022.learndico.LearnDicoApplication
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.data.entites.Words
import kotlin.concurrent.thread

class DaoViewModel (application: Application): AndroidViewModel(application) {

    /**
     * Cette classe de type ViewModel permet de charger les dictionnaires dans le fragment de séléction
     * des dictionnaires. Cela implique une connexion à room
     *
     * Documentation sur les ViewModel
     * https://developer.android.com/codelabs/basic-android-kotlin-training-shared-viewmodel#2
     */

    /* Connexion à room & requetes */
    private val dao = (application as LearnDicoApplication).getDataBase.getRequestDao()
    private val allDicoBD = MutableLiveData<List<Dico>>(emptyList())
    private val allLanguagesBd = MutableLiveData<List<Langues>>(emptyList())
    private val allWordsBd = MutableLiveData<List<Words>>(emptyList())
    private val languesSelected: MutableList<Langues> = mutableListOf<Langues>()
    private val resultPartialWord = MutableLiveData<List<Words>>(emptyList())

    fun loadAllDico() {
        thread {
            allDicoBD.postValue(dao.loadAllDico())
        }
    }

    fun loadAllLangues() {
        thread {
            allLanguagesBd.postValue(dao.loadAllLanguages())
        }
    }

    fun insertDico(){
        thread {
            dao.insertDictionnaire(Dico("Google", "https://www.google.fr", "", ""));
        }
    }

    fun insertLangues(langues: Langues) {
        thread {
            dao.insertLangues(langues)
        }
    }

    fun loadLanguages(languages: String){
        thread {
            languesSelected.addAll(dao.loadLanguages(languages))
        }
    }

    fun getAllDicoBD() : MutableLiveData<List<Dico>>{
        return allDicoBD;
    }

    fun getAllLanguagesBD() : MutableLiveData<List<Langues>>{
        return allLanguagesBd;
    }

    fun getLanguesSelected():MutableList<Langues>{
        return languesSelected;
    }

    fun getAllWordBD() : MutableLiveData<List<Words>>{
        return allWordsBd;
    }

    fun getResultPartialWord() : MutableLiveData<List<Words>> {
        return resultPartialWord
    }

    fun loadAllWord() {
        thread {
            allWordsBd.postValue(dao.loadAllWords())
        }
    }

    fun insertWord() {
        thread {
            //val wordOrigin: String, val wordTranslate: String, val languageOrigin: String, val languageTranslation: String, val wordSignification: String, val translationSignification: String, val url: String
            dao.insertMot(Words("Mot", "Lettre", "Chinois", "Francais", "blablabla", "franchement c'est ca", "https://www.william.fr" ));
            dao.insertMot(Words("AMot", "Lettre", "Chinois", "Francais", "a", "aa", "https://www.william.fr" ));
            dao.insertDictionnaire(Dico("Google", "https://google.com","anglais","francais"))
            dao.insertLangues(Langues("Français"))
            dao.insertLangues(Langues("Chinois"))
        }
    }

    fun loadPartialWords(s: String) {
        thread {
            resultPartialWord.postValue(dao.loadPartialWords(s))
        }
    }
}