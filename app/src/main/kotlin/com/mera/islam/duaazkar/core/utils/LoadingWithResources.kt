package  com.mera.islam.duaazkar.core.utils


sealed interface Resources<out T> {
    data object Loading: Resources<Nothing>
    data class SuccessList<T>(var data: List<T>): Resources<T>
}

sealed interface Result<out T> {
    data object Started: Result<Nothing>
    data object Completed: Result<Nothing>
    data class Success<T>(val data: T): Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
}
