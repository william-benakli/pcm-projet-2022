package fr.pcmprojet2022.learndico.sharedviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import fr.pcmprojet2022.learndico.data.entites.Dico

class SearchOnlineViewModel : ViewModel() {//(application: Application): AndroidViewModel(application) {


    /**
     * Cette classe de type ViewModel permet de stocker pour transferer le dictionnaire du fragmenent
     *  de séléction à celui de la recherche de mots en ligne
     *
     * Documentation sur les ViewModel
     * https://developer.android.com/codelabs/basic-android-kotlin-training-shared-viewmodel#2
     */

    /* Gestion getteur et setteur du dictionnaire */
    private var selectedDico : Dico? = null

    fun setSelectedDico(selected: Dico?){
        selectedDico = selected;
    }

    fun getSelectedDico(): Dico? {
        return selectedDico;
    }

}