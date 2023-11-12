package  com.mera.islam.duaazkar.core.workers.filDownloader

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import  com.mera.islam.duaazkar.core.workers.filDownloader.downloadProtocol.OkHttp3Downloader
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class FileDownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    @Inject
    lateinit var okHttp3Downloader: OkHttp3Downloader

    override suspend fun doWork(): Result {
        val url: String = inputData.getString("url") ?: return Result.failure(
            Data.Builder().putString("error", "Empty url").build()
        )
        val fileOutputPath: String = inputData.getString("fileInputPath") ?: return Result.failure(
            Data.Builder().putString("error", "File save path is empty").build()
        )

        createNotification()

        var workerResult = Result.success()

        okHttp3Downloader.downloadUrl(url, fileOutputPath).collect { result ->
            when (result) {
                is  com.mera.islam.duaazkar.core.utils.Result.Success -> {
                    val progress = result.data
                    //todo update notification
                }

                is  com.mera.islam.duaazkar.core.utils.Result.Completed -> {
                    //todo update user
                    workerResult = Result.success()
                }

                is  com.mera.islam.duaazkar.core.utils.Result.Error -> {
                    workerResult = Result.failure(
                        Data.Builder().putString(
                            "error",
                            result.exception?.message ?: result.exception?.localizedMessage ?: ""
                        ).build()
                    )
                }

                is  com.mera.islam.duaazkar.core.utils.Result.Started -> {
                    //todo update user
                }
            }
        }

        return workerResult
    }

    private fun createNotification() {

    }
}