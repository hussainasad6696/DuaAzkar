package  com.mera.islam.duaazkar.core.workers.filDownloader.downloadProtocol

import  com.mera.islam.duaazkar.core.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.FileOutputStream
import javax.inject.Inject

class OkHttp3Downloader @Inject constructor(
    private val client: OkHttpClient
) {
    fun downloadUrl(url: String,fileOutputPath: String): Flow<Result<Int>> = flow {
        emit(Result.Started)

        val request = Request.Builder()
            .url(url)
            .build()

        kotlin.runCatching {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                emit(Result.Error(Throwable("No response from url")))
                return@flow
            }

            val fOut = FileOutputStream(fileOutputPath)

            if (response.body == null) {
                emit(Result.Error(Throwable("File not found")))
                return@flow
            }

            val inputStream = response.body!!.byteStream().buffered()
            val byteArray = ByteArray(1024)
            var readBytes: Int
            var totalBytes = 0
            val fileSize = response.body!!.contentLength()

            while (inputStream.read(byteArray).also { readBytes = it } != -1) {
                totalBytes += readBytes
                fOut.write(byteArray,0,readBytes)

                Result.Success(((totalBytes.toFloat() / fileSize.toFloat()) * 100).toInt())

                delay(400L)
            }

            emit(Result.Completed)

        }.onFailure {
            emit(Result.Error(it))
        }
    }
}