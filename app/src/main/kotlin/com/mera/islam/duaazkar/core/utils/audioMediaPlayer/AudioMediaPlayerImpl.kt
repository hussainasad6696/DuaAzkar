package com.mera.islam.duaazkar.core.utils.audioMediaPlayer

import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AudioMediaPlayerImpl : AudioMediaPlayer,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener {

    private var mediaPlayer: MediaPlayer? = null
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var mediaPlayerEventListener: MediaPlayerEventListener? = null

    override fun setOnMediaPlayerEventListener(mediaPlayerEventListener: MediaPlayerEventListener) {
        this.mediaPlayerEventListener = mediaPlayerEventListener
    }

    override fun stateIn(scope: CoroutineScope) {
        this.scope = scope
    }

    override fun setMedia(mediaPath: String) {
        stopMediaPlayer()

        mediaPlayer = MediaPlayer().apply {
            setDataSource(mediaPath)
            prepare()
        }
    }

    override fun resumeMediaPlayer() {
        mediaPlayer?.start()
        startMediaPlayerProgressListener()
    }

    override fun pauseMediaPlayer() {
        mediaPlayer?.pause()
        mediaProgressJob?.cancel()
    }

    override fun stopMediaPlayer() {
        mediaPlayer?.apply {
            mediaProgressJob?.cancel()
            if (isPlaying)
                stop()
            reset()
            release()
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        stopMediaPlayer()
        mediaPlayerEventListener?.onCompleted()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer?.let {
            it.start()
            mediaPlayerEventListener?.onMaxDuration(it.duration)
            startMediaPlayerProgressListener()
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mediaPlayerEventListener?.onErrorListener("what $what == extra $extra")
        return false
    }

    private var mediaProgressJob: Job? = null
    private fun startMediaPlayerProgressListener() {
        if (mediaPlayer?.isPlaying == false) return

        mediaProgressJob?.cancel()
        mediaProgressJob = scope.launch {
            while (isActive) {
                mediaPlayer?.apply {
                    mediaPlayerEventListener?.onCurrentPosition(currentPosition)
                }
            }
        }
    }

    interface MediaPlayerEventListener {
        fun onErrorListener(message: String)
        fun onMaxDuration(duration: Int)
        fun onCurrentPosition(position: Int)
        fun onCompleted()
    }
}