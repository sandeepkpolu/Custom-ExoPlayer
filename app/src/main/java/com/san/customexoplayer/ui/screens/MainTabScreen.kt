package com.san.customexoplayer.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.san.customexoplayer.ui.viewmodels.VideoPlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTabScreen(viewModel: VideoPlayerViewModel) {

    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf(BottomTab.Videos) }

    Scaffold(
        topBar = { TopAppBar(title = {Text(text = "Custom Player", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue, titleContentColor = Color.White)) },
        bottomBar = {

            NavigationBar {
                BottomTab.entries.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(text = tab.label) },
                        selected = selectedTab == tab,
                        onClick = {
                            selectedTab = tab
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )

                }
            }

        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BottomTab.Videos.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomTab.Videos.route) {
                VideosScreen()
            }
            composable(BottomTab.Favorites.route) {
                FavoritesScreen(viewModel)
            }
        }

    }

}