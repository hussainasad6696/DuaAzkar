package com.mera.islam.duaazkar.core.workers.filDownloader

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.mera.islam.duaazkar.core.extensions.downloadService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DownloadManagerService @Inject constructor(@ApplicationContext context: Context) : CoroutineScope {

    private val downloadService = context.downloadService
    private lateinit var downloadRequest: DownloadManager.Request
    private var downloadId: Long = -1

    private var downloadProgressListener: DownloadProgressListener? = null
    private var downloadParamsListener: DownloadRequestNotCompletedListener? = null
    private var downloadFailedListener: DownloadFailedListener? = null
    private var downloadPausedListener: DownloadPausedListener? = null
    private var downloadCompletedListener: DownloadCompletedListener? = null
    private var downloadPendingListener: DownloadPendingListener? = null

    fun setOnDownloadProgressListener(progressListener: DownloadProgressListener): DownloadManagerService {
        this.downloadProgressListener = progressListener
        return this
    }

    fun setOnDownloadParamsIncompleteListener(downloadParamsIncomplete: DownloadRequestNotCompletedListener): DownloadManagerService {
        this.downloadParamsListener = downloadParamsIncomplete
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

    fun request(
        url: String,
        fileOutputPath: File,
        meteredOnly: Boolean //Set if download is allowed on Mobile network
    ): DownloadManagerService? {
        if (url.isEmpty()) {
            downloadParamsListener?.onDownloadRequestRemaining("Url is empty")
            return null
        }

        if (fileOutputPath.absolutePath.isEmpty()) {
            downloadParamsListener?.onDownloadRequestRemaining("Output path is empty")
            return null
        }


        downloadRequest = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(fileOutputPath))
            .setTitle("Downloading ${fileOutputPath.name}")
            .setDescription("Please wait while downloading media resources")
            .setRequiresCharging(false)
            .setAllowedOverMetered(meteredOnly)
            .setAllowedOverRoaming(false)

        return this
    }

    fun download() = launch {
        if (::downloadRequest.isInitialized) {
            downloadId = downloadService.enqueue(downloadRequest)
            downloadObserver()
        }
        else downloadParamsListener?.onDownloadRequestRemaining("Download request remaining")
    }

    private fun downloadObserver() = launch {
        val cursor: Cursor =
            downloadService.query(DownloadManager.Query().setFilterById(downloadId))
        val columnStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        val columnTotal = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
        val columnDownloadedBytes = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)

        while (isActive) {
            if (cursor.moveToFirst()) {

                when(cursor.getInt(columnStatus)) {
                    DownloadManager.STATUS_FAILED -> downloadFailedListener?.onDownloadFailed()
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
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun interface DownloadProgressListener {
        fun onProgressListener(progress: Int)
    }

    fun interface DownloadRequestNotCompletedListener {
        fun onDownloadRequestRemaining(message: String)
    }

    fun interface DownloadFailedListener {
        fun onDownloadFailed()
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