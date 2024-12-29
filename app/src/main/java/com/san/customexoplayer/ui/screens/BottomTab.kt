package com.san.customexoplayer.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow

enum class BottomTab(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Videos("videos", "Videos", Icons.Default.PlayArrow),
    Favorites("favorites", "Favorites", Icons.Default.Favorite)
}