package com.example.main.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.components.DefaultScreenUI
import com.example.main.components.MainNavTopBar
import com.example.main.components.OptionsBottomBar
import com.example.main.components.options.FirstLevelOptions
import com.example.main.utils.convertToImageBitmap

@Composable
fun MainScreen(
    imageUri: Uri
) {
    val bitmapImage = convertToImageBitmap(LocalContext.current, imageUri)

    DefaultScreenUI {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val itemWidth = maxWidth
            val itemHeight = maxHeight - 150.dp


            Column {

                MainNavTopBar()

                Image(
                    bitmap = bitmapImage,
                    contentDescription = "",
                    modifier = Modifier
                        .width(itemWidth)
                        .height(itemHeight)
                )
            }

            OptionsBottomBar(
                modifier = Modifier.align(Alignment.BottomStart),
                photoOptions = listOf(
                    FirstLevelOptions.TuneOption
                )
            )
        }
    }

}