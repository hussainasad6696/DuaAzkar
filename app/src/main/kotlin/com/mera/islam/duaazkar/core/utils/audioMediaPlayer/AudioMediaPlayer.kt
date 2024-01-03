package com.mera.islam.duaazkar.core.utils.audioMediaPlayer

import kotlinx.coroutines.CoroutineScope

interface AudioMediaPlayer {
    fun setOnMediaPlayerEventListener(mediaPlayerEventListener: AudioMediaPlayerImpl.MediaPlayerEventListener)
    fun stateIn(scope: CoroutineScope)
    fun setMedia(mediaPath: String)
    fun resumeMediaPlayer()
    fun pauseMediaPlayer()
    fun stopMediaPlayer()
}