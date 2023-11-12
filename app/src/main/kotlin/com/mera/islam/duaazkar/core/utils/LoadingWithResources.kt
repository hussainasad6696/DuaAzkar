package  com.mera.islam.duaazkar.core.utils

data class LoadingWithResources<T>(
    val isLoading: Boolean = false,
    val resources: List<T> = emptyList()
)

sealed interface LoadingResources<out T> {
    data object Loading: LoadingResources<Nothing>
    data class SuccessList<T>(var data: List<T>): LoadingResources<T>
}

sealed interface Result<out T> {
    data object Started: Result<Nothing>
    data object Completed: Result<Nothing>
    data class Success<T>(val data: T): Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
}
