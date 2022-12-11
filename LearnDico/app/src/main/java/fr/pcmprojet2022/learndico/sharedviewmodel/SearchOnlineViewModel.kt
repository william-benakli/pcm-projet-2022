package fr.pcmprojet2022.learndico.sharedviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fr.pcmprojet2022.learndico.data.entites.Dico

class SearchOnlineViewModel (application: Application): AndroidViewModel(application) {

    /*
        Cette classe permet de transferer le dictiionnaire du fragmenent
        de séléction des dictionnaires à celui de la recherche de mots en ligne

     */

    /* Le dictionnaire est null les verifications impliqueront une recherche à null ou 0 sinon*/
    private var selected_dico : Dico? = null

    fun setSelectedDico(selected: Dico){
        selected_dico = selected;
    }

    fun getSelectedDico(): Dico? {
        return selected_dico;
    }

}