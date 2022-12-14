package fr.pcmprojet2022.learndico.sharedviewmodel

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.pcmprojet2022.learndico.LearnDicoApplication
import fr.pcmprojet2022.learndico.MainActivity
import fr.pcmprojet2022.learndico.data.LearnDicoBD
import fr.pcmprojet2022.learndico.data.entites.Dico
import kotlin.concurrent.thread

class SharedViewModel(application: Application): AndroidViewModel(application) {

    private val dao = (application as LearnDicoApplication).getDataBase.getRequestDao()
    private var mot = MutableLiveData("");
    private var motLiveData : LiveData<String> = mot;


    fun saveMot(msg: String){
        mot.value = msg
        println(getApplication())
    }

    val allDicoBD = MutableLiveData<List<Dico>>(emptyList())
    fun loadAllDico() {
        thread {
            allDicoBD.postValue(dao.loadAllDico())
        }
    }

    fun insertDico(){
        thread {
                dao.insertDictionnaire(Dico( "Google", "www.google.fr", 0, 0));
                dao.insertDictionnaire(Dico( "LaRousse", "www.LaRousse.fr", 0, 0));
                dao.insertDictionnaire(Dico( "FrancTv", "www.LaRousse.fr", 0, 0));
        }
    }


    fun saveDicoSelection() {
        TODO("Not yet implemented")
    }

    fun clearData(){
        
    }

    fun getDicoStats(): Int {
        return 0;
    }

    //var dicoSelect : LiveData<Dico>

    fun getDico(dicoStats: Int): Dico? {
        thread {
        //    dicoSelect.value.(dao.selectDicoFromId(dicoStats));
        }
        return null// dicoSelect.value;
    }

}