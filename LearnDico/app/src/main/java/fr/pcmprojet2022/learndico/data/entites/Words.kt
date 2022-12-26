package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity

@Entity(primaryKeys = ["url"])
data class Words(val wordOrigin: String, val wordTranslate: String, val languageOrigin: String, val languageTranslation: String, val wordSignification: String, val translationSignification: String, val url: String)