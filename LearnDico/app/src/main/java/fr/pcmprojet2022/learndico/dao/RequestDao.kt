package fr.pcmprojet2022.learndico.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.data.entites.Words

@Dao
interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMot(vararg words: Words) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDictionnaire(vararg dictionnaire: Dico) :  List<Long>

    @Query("SELECT * FROM words")
    fun loadAllWords() :  List<Words>

    @Query("SELECT * FROM dico")
    fun loadAllDico() :  List<Dico>

    @Query("SELECT * FROM langues")
    fun loadAllLanguages():  List<Langues>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLangues(vararg langues: Langues) :  List<Long>

    @Query("SELECT * FROM langues WHERE languages=:languages_name")
    fun loadLanguages(languages_name: String): List<Langues>

    @Query("SELECT * FROM dico WHERE nom IN (:nomDico) AND url IN(:urlDico)")
    fun loadDico(urlDico: String, nomDico: String): List<Dico>

    @Query("SELECT * FROM words WHERE wordOrigin like :s || '%' ")
    fun loadPartialWords(s: String): List<Words>

    @Query("SELECT * FROM words WHERE url IN (:url)")
    fun loadWords(url: String): List<Words>

    @Update
    fun updateWord(word: Words) : Int

    @Query("SELECT * FROM words WHERE remainingUses > 0")
    fun loadAllWordsAvailableNotif() : LiveData<List<Words>>

    @Query("SELECT * FROM words WHERE url=:key")
    fun getWordByKey(key: String) : Words?

    @Delete
    fun deleteWord(word: Words)

    @Query("SELECT * FROM words WHERE remainingUses <= 0")
    fun getWordsByRemainingUses() : List<Words>

}