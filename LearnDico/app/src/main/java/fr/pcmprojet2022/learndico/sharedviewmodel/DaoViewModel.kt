package fr.pcmprojet2022.learndico.sharedviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import fr.pcmprojet2022.learndico.LearnDicoApplication
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Langues
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
            dao.insertDictionnaire(Dico("Google", "https://www.google.fr", 0, 0));
            dao.insertDictionnaire(Dico( "Larousse", "https://www.Larousse.fr", 0, 0));
            dao.insertDictionnaire(Dico( "Reverso", "https://www.Reverso.fr", 0, 0));
            dao.insertDictionnaire(Dico( "UnivParis", "https://www.UnivParis.fr", 0, 0));
        }
    }

    fun insertLangues(){
        thread {
            dao.insertLangues(Langues( "Français"))
            dao.insertLangues(Langues( "Anglais"))
            dao.insertLangues(Langues( "Espagnol"))
            dao.insertLangues(Langues("Allemand"))
            dao.insertLangues(Langues("Chinois"))
        }
    }

    fun getAllDicoBD() : MutableLiveData<List<Dico>>{
        return allDicoBD;
    }

    fun getAllLanguagesBD() : MutableLiveData<List<Langues>>{
        return allLanguagesBd;
    }

}