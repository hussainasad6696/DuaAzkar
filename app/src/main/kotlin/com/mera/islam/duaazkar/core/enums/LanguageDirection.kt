package  com.mera.islam.duaazkar.core.enums

enum class LanguageDirection(val direction: Int) {
    LEFT(0),
    RIGHT(1);
}

fun Int.toLanguageDirection(): LanguageDirection {
    return when (this) {
        LanguageDirection.LEFT.direction -> LanguageDirection.LEFT
        LanguageDirection.RIGHT.direction -> LanguageDirection.RIGHT
        else -> LanguageDirection.LEFT
    }
}