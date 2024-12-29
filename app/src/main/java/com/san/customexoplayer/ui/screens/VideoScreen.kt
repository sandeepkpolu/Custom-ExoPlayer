package com.san.customexoplayer.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.san.customexoplayer.data.VideoTypes
import com.san.customexoplayer.ui.activities.VideoActivity

@Composable
fun VideosScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(VideoTypes.entries) {
                Text(text = it.type,
                    modifier = Modifier.padding(16.dp)
                        .clickable {
                            val intent = Intent(context, VideoActivity::class.java)
                            intent.putExtra(
                                "video_url",
                                it.path
                            )
                            intent.putExtra(
                                "video_type",
                                it.type
                            )
                            context.startActivity(intent)
                        },
                    color = Color.Black,
                    fontSize = 30.sp,
                    )
                HorizontalDivider()
            }
        }
    }

}