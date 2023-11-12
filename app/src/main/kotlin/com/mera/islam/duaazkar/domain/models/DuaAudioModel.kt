package  com.mera.islam.duaazkar.domain.models

data class DuaAudioModel(
    val duaId: Int,
    val url: String,
    val recitationBy: String?,
    val reciterImageUrl: String?,
    val id: Int = 0
)