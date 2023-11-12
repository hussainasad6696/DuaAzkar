package  com.mera.islam.duaazkar.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dua")
data class DuaEntity(
    @ColumnInfo("arabic")
    val arabic: String,
    @ColumnInfo("translitration")
    val translitration: String,
    @ColumnInfo("reason")
    val reason: String,
    @ColumnInfo("method")
    val method: String?,
    @ColumnInfo("reference_from")
    val referenceFrom: String,
    @ColumnInfo("reference_type")
    val referenceType: String,
    @ColumnInfo("dua_type")
    val duaType: Int,
    @ColumnInfo("is_fav")
    val isFav: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
