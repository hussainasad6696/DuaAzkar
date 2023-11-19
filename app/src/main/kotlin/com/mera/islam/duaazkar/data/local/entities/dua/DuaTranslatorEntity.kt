package  com.mera.islam.duaazkar.data.local.entities.dua

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dua_translators")
data class DuaTranslatorEntity(
    @ColumnInfo("language_code")
    val languageCode: String,
    @ColumnInfo("language_name")
    val languageName: String,
    @ColumnInfo("language_direction")
    val languageDirection: Int,
    @ColumnInfo("translator_name")
    val translatorName: String?,
    @ColumnInfo("translator_image_url")
    val translatorImageUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}