package com.san.customexoplayer.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.san.customexoplayer.ui.screens.MainTabScreen
import com.san.customexoplayer.ui.theme.CustomExoPlayerTheme
import com.san.customexoplayer.ui.viewmodels.VideoPlayerViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: VideoPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomExoPlayerTheme {
                MainTabScreen(viewModel)
            }
        }
    }
}