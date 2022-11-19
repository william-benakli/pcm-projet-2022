package fr.pcmprojet2022.learndico.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import fr.pcmprojet2022.learndico.data.Mot

@Dao
interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertMot(vararg words: Mot) : List<Long>

}