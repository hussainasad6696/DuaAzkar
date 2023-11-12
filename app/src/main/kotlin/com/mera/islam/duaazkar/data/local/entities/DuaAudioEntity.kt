package  com.mera.islam.duaazkar.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    "dua_audio", foreignKeys = [
        ForeignKey(
            entity = DuaEntity::class,
            parentColumns = ["id"],
            childColumns = ["dua_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ], indices = [
        androidx.room.Index(value = ["dua_id"]),
    ]
)
data class DuaAudioEntity(
    @ColumnInfo("dua_id")
    val duaId: Int,
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("recitation_by")
    val recitationBy: String?,
    @ColumnInfo("reciter_image_url")
    val reciterImageUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
