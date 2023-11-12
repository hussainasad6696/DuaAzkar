package  com.mera.islam.duaazkar.domain.models

data class DuaModel(
    val arabic: String,
    val translitration: String,
    val reason: String,
    val referenceFrom: String,
    val referenceType: String,
    val method: String?,
    val duaType: DuaType,
    val isFav: Boolean,
    val id: Int
)