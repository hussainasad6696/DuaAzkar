package com.mera.islam.duaazkar.core.workers.filDownloader

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import androidx.core.net.toUri
import com.mera.islam.duaazkar.core.DUA_DIR
import com.mera.islam.duaazkar.core.downloadService
import com.mera.islam.duaazkar.core.duaDownloadAddress
import com.mera.islam.duaazkar.core.extensions.log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL
import java.net.URLConnection
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DownloadManagerService @Inject constructor(@ApplicationContext private val context: Context) :
    CoroutineScope {

    private val downloadService = context.downloadService
    private lateinit var downloadRequest: DownloadManager.Request
    private var downloadId: Long = -1

    private var downloadProgressListener: DownloadProgressListener? = null
    private var downloadFailedListener: DownloadFailedListener? = null
    private var downloadPausedListener: DownloadPausedListener? = null
    private var downloadCompletedListener: DownloadCompletedListener? = null
    private var downloadPendingListener: DownloadPendingListener? = null

    fun setOnDownloadProgressListener(progressListener: DownloadProgressListener): DownloadManagerService {
        this.downloadProgressListener = progressListener
        return this
    }

    fun setOnDownloadFailedListener(downloadFailedListener: DownloadFailedListener): DownloadManagerService {
        this.downloadFailedListener = downloadFailedListener
        return this
    }

    fun setOnDownloadPausedListener(downloadPausedListener: DownloadPausedListener): DownloadManagerService {
        this.downloadPausedListener = downloadPausedListener
        return this
    }

    fun setOnDownloadCompletedListener(downloadCompletedListener: DownloadCompletedListener): DownloadManagerService {
        this.downloadCompletedListener = downloadCompletedListener
        return this
    }

    fun setOnDownloadPendingListener(downloadPendingListener: DownloadPendingListener): DownloadManagerService {
        this.downloadPendingListener = downloadPendingListener
        return this
    }

    private fun mimeType(urlString: String): String? = runCatching {
        val url = URL(urlString)
        val connection: URLConnection = url.openConnection()
        connection.connect()

        connection.contentType
    }.getOrNull()

    private var url: String = ""
    private var directoryPath: String = ""
    private var fileName: String = ""

    fun request(
        url: String,
        directoryPath: String,
        fileName: String,
        meteredOnly: Boolean = false //Set if download is allowed on Mobile network
    ): DownloadManagerService? {
        if (url.isEmpty()) {
            downloadFailedListener?.onDownloadFailed("Url is empty")
            return null
        }

        this.url = url

        if (fileName.isEmpty()) {
            downloadFailedListener?.onDownloadFailed("Output path is empty")
            return null
        }

        this.fileName = fileName

        if (directoryPath.isEmpty()) {
            downloadFailedListener?.onDownloadFailed("Directory path is empty")
            return null
        }

        this.directoryPath = directoryPath

        if (File(context.getExternalFilesDir(directoryPath), fileName).exists()) {
            downloadFailedListener?.onDownloadFailed("File already exist")
            return null
        }


        downloadRequest = DownloadManager.Request(url.toUri())
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(context, directoryPath, fileName)
            .setTitle("Downloading $fileName")
            .setDescription("Please wait while downloading media resources")
            .setRequiresCharging(false)
            .setAllowedOverMetered(meteredOnly)
            .setAllowedOverRoaming(false)

        mimeType(url)?.let {
            downloadRequest.setMimeType(it)
        }

        return this
    }

    fun download() = launch {
        if (::downloadRequest.isInitialized) {
            downloadId = downloadService.enqueue(downloadRequest)
            downloadObserver()
        } else downloadFailedListener?.onDownloadFailed("Download request remaining")
    }

    private fun downloadObserver() = launch {
        while (isActive) {
            val cursor: Cursor =
                downloadService.query(DownloadManager.Query().setFilterById(downloadId))
            val columnStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            val columnTotal = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
            val columnDownloadedBytes =
                cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)

            if (cursor.moveToFirst()) {
                when (cursor.getInt(columnStatus)) {
                    DownloadManager.STATUS_FAILED -> {
                        downloadFailedListener?.onDownloadFailed("Download failed")
                        this.cancel()
                    }

                    DownloadManager.STATUS_PAUSED -> downloadPausedListener?.onDownloadPaused()
                    DownloadManager.STATUS_PENDING -> downloadPendingListener?.onDownloadPending()
                    DownloadManager.STATUS_RUNNING -> {
                        val total = cursor.getLong(columnTotal)

                        if (total >= 0) {
                            val downloaded = cursor.getLong(columnDownloadedBytes)
                            downloadProgressListener?.onProgressListener(((downloaded * 100L) / total).toInt())
                        }
                    }

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        downloadCompletedListener?.onDownloadCompleted()
                        this.cancel()
                    }
                }
            } else {
                downloadFailedListener?.onDownloadFailed("Download canceled")
                runCatching { File(context.getExternalFilesDir(directoryPath), fileName).delete() }
                this.cancel()
            }

            delay(400L)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun interface DownloadProgressListener {
        fun onProgressListener(progress: Int)
    }


    fun interface DownloadFailedListener {
        fun onDownloadFailed(message: String)
    }

    fun interface DownloadPausedListener {
        fun onDownloadPaused()
    }

    fun interface DownloadCompletedListener {
        fun onDownloadCompleted()
    }

    fun interface DownloadPendingListener {
        fun onDownloadPending()
    }
}

fun main() {
    val audioNames = listOf("n1", "n3", "n44")


}