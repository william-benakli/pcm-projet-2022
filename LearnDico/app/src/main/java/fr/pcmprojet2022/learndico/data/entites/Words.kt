package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Words(@PrimaryKey(autoGenerate = true) var key: Int, val word: String, val language: String, val translation: String, val wordSignification: String, val translationSignification: String)