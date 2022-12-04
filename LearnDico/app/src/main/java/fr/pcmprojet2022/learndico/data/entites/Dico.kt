package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
class Dico(@PrimaryKey var key: Int, var nom: String, val url: String, val src_id: Int, val dst_id: Int){};

/*

(foreignKeys = [ForeignKey(entity = Langues::class,
    parentColumns = ["id"],
    childColumns = ["src_id"]),
    ForeignKey(entity = Langues::class,
        parentColumns = ["id"],
        childColumns = ["dst_id"])])
 */