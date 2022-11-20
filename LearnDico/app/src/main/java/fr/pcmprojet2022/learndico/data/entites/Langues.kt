package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Langues(@PrimaryKey(autoGenerate = true) var id: Long, vararg languages: String){}