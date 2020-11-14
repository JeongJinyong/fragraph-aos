package com.depromeet.fragraph.core.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class Player {

    private var runFlag = false;

    private var mediaPlayer: MediaPlayer? = null

    fun setPlayer(url: String, preparedListener: MediaPlayer.OnPreparedListener) {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }

        mediaPlayer = MediaPlayer().apply {
            this.setOnPreparedListener(preparedListener)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(url)
            prepareAsync() // might take long! (for buffering, etc)
        }
    }

    fun getMediaPlayer(): MediaPlayer? = mediaPlayer

    fun start() {
        mediaPlayer?.let {
            Timber.d("player start")
            runFlag = true
            it.start()
        }
    }

    fun pause() {
        mediaPlayer?.let {
            Timber.d("player pause")
            runFlag = false
            it.pause()
        }
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: run { false }
    }

    fun duration(): Int {
        return mediaPlayer?.duration ?: run { 0 }
    }

    fun remainingTime(): Int {
        return mediaPlayer?.let { it.duration - it.currentPosition } ?: run { 0 }
    }

    fun remainingTimeFlow(): Flow<Int> = flow {
        while (this@Player.remainingTime() > 0 && runFlag) {
            delay(1000)
            emit(this@Player.remainingTime())
        }

    }.flowOn(Dispatchers.IO)

    fun releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
            runFlag = false
        }
    }
}