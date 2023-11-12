package  com.mera.islam.duaazkar.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dua_translation", foreignKeys = [
        ForeignKey(
            entity = DuaTranslatorEntity::class,
            parentColumns = ["id"],
            childColumns = ["translator_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ), ForeignKey(
            entity = DuaEntity::class,
            parentColumns = ["id"],
            childColumns = ["dua_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ], indices = [
        Index(value = ["translator_id"]),
        Index(value = ["dua_id"])
    ]
)
data class DuaTranslationEntity(
    @ColumnInfo("dua_id")
    val duaId: Int,
    @ColumnInfo("translation")
    val translation: String,
    @ColumnInfo("translator_id")
    val translatorId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
