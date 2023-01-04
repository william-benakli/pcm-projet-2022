package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Words(var wordOrigin: String, var wordTranslate: String, val languageOrigin: String, val languageTranslation: String, var wordSignification: String, var translationSignification: String, @PrimaryKey val url: String, var fileName: String?, var remainingUses: Int)