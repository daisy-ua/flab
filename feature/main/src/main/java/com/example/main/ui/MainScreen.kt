package com.example.main.ui

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.components.DefaultScreenUI
import com.example.imagesource.SourceViewModel
import com.example.main.components.MainNavTopBar
import com.example.main.components.OptionsBottomBar
import com.example.main.components.options.FirstLevelOptions
import com.example.main.utils.convertToBitmap

@Composable
fun MainScreen(
    sourceViewModel: SourceViewModel = viewModel(),
    navigateToTuneScreen: () -> Unit
) {

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
                    bitmap = sourceViewModel.currentSource!!.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .width(itemWidth)
                        .height(itemHeight)
                )
            }

            OptionsBottomBar(
                modifier = Modifier.align(Alignment.BottomStart),
                photoOptions = listOf(
                    Pair(FirstLevelOptions.TuneOption, navigateToTuneScreen)
                )
            )
        }
    }

}