package fr.pcmprojet2022.learndico.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Words

@Dao
interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertMot(vararg words: Words) : List<Long>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertDictionnaire(vararg dictionnaire: Dico) : List<Long>

    @Query("SELECT * FROM words")
    fun loadAllWords() : List<Words>

    @Query("SELECT * FROM dico")
    fun loadAllDico() : List<Dico>

    @Query("SELECT * FROM dico WHERE 'key' IN (:id)")
    fun selectDicoFromId(id : Int) : Dico
}