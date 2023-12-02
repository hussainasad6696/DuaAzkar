package  com.mera.islam.duaazkar.core.utils


sealed interface EventResources<out T> {
    data object Loading: EventResources<Nothing>
    data class SuccessList<T>(var list: List<T>): EventResources<T>
}

sealed interface EventResult<out T> {
    data object Started: EventResult<Nothing>
    data object Completed: EventResult<Nothing>
    data class Success<T>(val resource: T): EventResult<T>
    data class Error(val exception: Throwable? = null) : EventResult<Nothing>
}
