package com.example.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.components.DefaultScreenUI
import com.example.main.R

@Composable
fun DefaultOptionScreen(
    modifier: Modifier = Modifier,
    bitmapImage: ImageBitmap?,
    onSave: () -> Unit = { },
    bottomSheet: @Composable () -> Unit = { },
) {
    DefaultScreenUI {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val itemWidth = maxWidth
            val itemHeight = maxHeight - 150.dp

            Column {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    NavBarTextBtn(
                        text = stringResource(id = R.string.save_image),
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onCLick = onSave
                    )
                }

                bitmapImage?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "",
                        modifier = Modifier
                            .width(itemWidth)
                            .height(itemHeight)
                    )
                }
            }

            Box(modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)) {
                bottomSheet()
            }

        }
    }

}