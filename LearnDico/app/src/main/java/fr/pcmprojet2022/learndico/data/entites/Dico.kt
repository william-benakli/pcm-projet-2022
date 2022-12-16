package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity
class Dico(@PrimaryKey var nom: String, val url: String, val src: String, val dst: String)


/*

(foreignKeys = [ForeignKey(entity = Langues::class, parentColumns = ["id"], childColumns = ["src_id"]),
        ForeignKey(entity = Langues::class, parentColumns = ["id"], childColumns = ["dst_id"])])
 */

