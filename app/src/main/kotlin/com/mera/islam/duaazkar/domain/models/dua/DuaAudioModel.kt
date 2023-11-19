package  com.mera.islam.duaazkar.domain.models.dua

data class DuaAudioModel(
    val duaId: Int,
    val url: String,
    val recitationBy: String?,
    val reciterImageUrl: String?,
    val id: Int = 0
)