package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity

@Entity
data class Words(val word: String, val language: String, val translation: String, val wordSignification: String, val translationSignification: String)