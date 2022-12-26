package fr.pcmprojet2022.learndico.sharedviewmodel

import androidx.lifecycle.ViewModel
import fr.pcmprojet2022.learndico.data.entites.Words

class ModifiedWordViewModel: ViewModel(){

    private var words : Words? = null

    fun setWord(wordsRest: Words){
        words = wordsRest
    }

    fun getWord(): Words? {
        return this.words
    }

}