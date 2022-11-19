package fr.pcmprojet2022.learndico.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mot(@PrimaryKey(autoGenerate = true) var idAuthor: Long, val word: String, val language: String, val translation: String, val wordSignification: String, val translationSignification: String)