package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Words(val wordOrigin: String, val wordTranslate: String, val languageOrigin: String, val languageTranslation: String, val wordSignification: String, val translationSignification: String, @PrimaryKey val url: String,var fileName: String?, var remainingUses: Int)