package  com.mera.islam.duaazkar.core.utils


sealed interface UiStates<out T> {
    data object Loading: UiStates<Nothing>
    data class Success<T>(var template: T): UiStates<T>
}

sealed interface EventResult<out T> {
    data object Started: EventResult<Nothing>
    data object Completed: EventResult<Nothing>
    data class Success<T>(val resource: T): EventResult<T>
    data class Error(val exception: Throwable? = null) : EventResult<Nothing>
}
