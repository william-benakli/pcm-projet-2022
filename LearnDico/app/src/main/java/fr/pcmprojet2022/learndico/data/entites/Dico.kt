package fr.pcmprojet2022.learndico.data.entites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Langues::class,
    parentColumns = ["id"],
    childColumns = ["src_id"]),
    ForeignKey(entity = Langues::class,
        parentColumns = ["id"],
        childColumns = ["dst_id"])])
class Dico(@PrimaryKey(autoGenerate = true) var key: Int, val url: String, val src_id: Int, val dst_id: Int){};


/*


    ForeignKey(
        entity = Author::class,
        parentColumns = [ "id" ],
        childColumns = [ "idAuthor" ],
        deferred = true,
        onDelete = ForeignKey.CASCADE
)
 */
