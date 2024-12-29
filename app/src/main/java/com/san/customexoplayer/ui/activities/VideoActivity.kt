package com.san.customexoplayer.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.san.customexoplayer.R
import com.san.customexoplayer.data.VideoTypes
import com.san.customexoplayer.database.FavoriteVideo
import com.san.customexoplayer.ui.viewmodels.VideoPlayerViewModel
import kotlinx.coroutines.launch

class VideoActivity: AppCompatActivity() {

    lateinit var exoPlayer: ExoPlayer
    lateinit var playerView: PlayerView
    var videoURL: String? = null
    var videoType: String? = null

    private var playPauseButton: ImageView? = null
    private var favoriteButton: ImageView? = null
    private var volumeControl: SeekBar? = null
    private var brightnessControl: SeekBar? = null
    private var playerSeekBar: SeekBar? = null
    private lateinit var currentTime: TextView
    private lateinit var totalTime: TextView
    private var audioManager: AudioManager? = null
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var isFavorite = false
    private val viewModel: VideoPlayerViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_video_player)
        playerView = findViewById(R.id.player_view)
        playPauseButton = findViewById(R.id.btn_play)
        playerSeekBar = findViewById(R.id.player_seek_bar)
        currentTime = findViewById(R.id.tv_current_time)
        totalTime = findViewById(R.id.tv_total_time)
        favoriteButton = findViewById(R.id.btn_favorite)
        videoURL = intent.getStringExtra("video_url") ?: return
        videoType = intent.getStringExtra("video_type") ?: return

        initExoPlayer()
        setupCustomControls()
        setVolumeControl()
        setupBrightnessController()
        lifecycleScope.launch {
            isFavorite = viewModel.isFavorite(videoURL!!)
            updateFavoriteIcon()
        }
    }

    @OptIn(UnstableApi::class)
    private fun initExoPlayer() {
        exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(DefaultMediaSourceFactory(this)) // Ensures HLS support
            .build()
        playerView.player = exoPlayer

        if (videoType == VideoTypes.DRM_SUPPORT.type) {
            // Create DRM configuration
            val drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                .setLicenseUri(getString(R.string.drm_license_url))
                .build()

            // Create MediaItem with DRM configuration
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(videoURL))
                .setDrmConfiguration(drmConfiguration)
                .build()
            exoPlayer.setMediaItem(mediaItem)
        } else {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoURL))
            exoPlayer.setMediaItem(mediaItem)
        }
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        playPauseButton?.setOnClickListener {
            if(exoPlayer.isPlaying) {
                exoPlayer.pause()
                playPauseButton?.setImageResource(R.drawable.ic_play)
            } else {
                exoPlayer.play()
                playPauseButton?.setImageResource(R.drawable.ic_pause)
            }
        }
        exoPlayer.addListener(object: Player.Listener {

            override fun onPlaybackStateChanged(@Player.State state: Int) {

                if (state == Player.STATE_READY && exoPlayer.isPlaying) {
                    playPauseButton?.setImageResource(R.drawable.ic_pause)
                    totalTime.text = formatTime(exoPlayer.duration)
                    playerSeekBar?.max = exoPlayer.duration.toInt()
                } else {
                    playPauseButton?.setImageResource(R.drawable.ic_play)
                }

            }
        })
        playerView.controllerHideOnTouch = true
        updatePlayerProgress()
    }

    private fun setupCustomControls() {
        favoriteButton?.setOnClickListener {
            lifecycleScope.launch {
                if (isFavorite) {
                    viewModel.removeFavorite(FavoriteVideo(videoURL!!, videoType!!))
                } else {
                    viewModel.addFavorite(FavoriteVideo(videoURL!!, videoType!!))
                }
                isFavorite = !isFavorite
                updateFavoriteIcon()
            }
        }
        findViewById<ImageView>(R.id.btn_share).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, exoPlayer.currentMediaItem?.mediaId ?: "")
            }
            startActivity(shareIntent)
        }
        findViewById<ImageView>(R.id.btn_rewind).setOnClickListener {
            exoPlayer.seekBack()
        }
        findViewById<ImageView>(R.id.btn_forward).setOnClickListener {
            exoPlayer.seekForward()
        }

        playerSeekBar?.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    exoPlayer.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun setVolumeControl() {
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        volumeControl = findViewById(R.id.volume_control)
        volumeControl?.max = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 0
        volumeControl?.progress = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0

        volumeControl?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun setupBrightnessController() {
        brightnessControl = findViewById(R.id.brightness_control)
        brightnessControl?.max = 255
        brightnessControl?.progress = Settings.System.getInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            125
        )
        brightnessControl?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val lp = window.attributes
                lp.screenBrightness = progress / 255.0f
                window.attributes = lp
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun updatePlayerProgress() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (exoPlayer.isPlaying) {
                    val position = exoPlayer.currentPosition.toInt()
                    playerSeekBar?.progress = position
                    currentTime.text = formatTime(position.toLong())
                }
                handler.postDelayed(this, 500)
            }
        }, 0)
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(milliseconds: Long): String {
        val minutes = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateFavoriteIcon() {
        favoriteButton?.setImageResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_unfavorite)
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.playWhenReady = false
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }

}