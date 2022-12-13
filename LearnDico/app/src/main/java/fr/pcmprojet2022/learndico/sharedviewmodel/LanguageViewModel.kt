package fr.pcmprojet2022.learndico.sharedviewmodel

import androidx.lifecycle.ViewModel
import fr.pcmprojet2022.learndico.data.entites.Langues

class LanguageViewModel : ViewModel() {//(application: Application): AndroidViewModel(application) {


    /**
     * Cette classe de type ViewModel permet de stocker pour transferer le dictionnaire du fragmenent
     *  de séléction à celui de la selection de languages
     *
     * Documentation sur les ViewModel
     * https://developer.android.com/codelabs/basic-android-kotlin-training-shared-viewmodel#2
     */

    /* Gestion getteur et setteur du dictionnaire */
    private var selectedLanguesSource : Langues? = null
    private var selectedLanguesDestionation : Langues? = null

    fun setSelectedLangueDest(selected: Langues?){
        selectedLanguesSource = selected;
    }

    fun getSelectedLangueDest(): Langues? {
        return selectedLanguesSource;
    }

    fun setSelectedLangueSrc(selected: Langues?){
        selectedLanguesDestionation = selected;
    }

    fun getSelectedLangueSrc(): Langues? {
        return selectedLanguesDestionation;
    }

}