package fr.pcmprojet2022.learndico.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.pcmprojet2022.learndico.dao.RequestDao
import fr.pcmprojet2022.learndico.data.entites.Dico
import fr.pcmprojet2022.learndico.data.entites.Langues
import fr.pcmprojet2022.learndico.data.entites.Words
import android.content.Context

@Database(entities = [Words::class, Dico::class, Langues::class], version=23)
abstract class LearnDicoBD : RoomDatabase() {

    /**
     * Abstract classe LearnDicoBD est un singleton permet d'avoir une unique instance de connexion à la base de donnée ROOM
     */

    abstract fun getRequestDao(): RequestDao

    companion object {

        @Volatile
        private var uniqueInstance: LearnDicoBD? = null

        fun getInstanceBD(context : Context): LearnDicoBD {
            if (uniqueInstance != null) return uniqueInstance as LearnDicoBD
            val db = Room
                .databaseBuilder(context.applicationContext, LearnDicoBD::class.java, "learndico_bdd")
                .fallbackToDestructiveMigration()
                .build()
            uniqueInstance = db
            return uniqueInstance!!
        }

    }
}