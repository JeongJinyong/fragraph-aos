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

    private var runFlag = false

    private var maxTime = 0
    private var flowTime = 0

    private var mediaPlayer: MediaPlayer? = null

    fun setPlayer(
        url: String,
        maxTime: Int,
        preparedListener: MediaPlayer.OnPreparedListener
    ) {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }

        this.maxTime = maxTime
        this.flowTime = 0
        Timber.d("init MediaPlayer")
        mediaPlayer = MediaPlayer().apply {
            this.setOnPreparedListener(preparedListener)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnCompletionListener {
                if (this@Player.remainingTime() > 0) {
                    it.seekTo(0)
                    it.start()
                }
            }
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
        return if(maxTime - flowTime > 0) maxTime - flowTime else 0
    }

    fun remainingTimeFlow(): Flow<Int> = flow {
        while (this@Player.remainingTime() > 0 && runFlag) {
            delay(1000)
            flowTime += 1000
            emit(this@Player.remainingTime())
        }
        if (!runFlag) {
            emit(PAUSED)
        }
        if (this@Player.remainingTime() <= 0) {
            emit(FINISHED)
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

    companion object {
        const val FINISHED = -1
        const val PAUSED = 0
    }
}