package com.example.main.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.main.models.IPhotoOption
import com.example.main.ui.options.effects.data.Effects

@Composable
fun OptionsBottomBar(
    modifier: Modifier = Modifier,
    photoOptions: List<Pair<IPhotoOption, () -> Unit>>,
    currentSelectedId: Int? = null,
    contentAlignment: Alignment = Alignment.BottomCenter,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
//        TODO: set min size depends on longest option name
        val thumbnailSize = 70.dp

        LazyRow {
            items(photoOptions) { option ->
                OptionCard(
                    modifier = modifier
                        .width(thumbnailSize)
                        .aspectRatio(1f),
                    painter = painterResource(id = option.first.drawableId),
                    title = stringResource(option.first.nameId),
                    isSelected = currentSelectedId == option.first.nameId,
                    onClick = option.second
                )
            }
        }
    }

}

@Composable
fun BitmapOptionsBottomBar(
    modifier: Modifier = Modifier,
    photoOptions: List<Pair<Effects, () -> Any>>,
    currentSelectedId: Int? = null,
    contentAlignment: Alignment = Alignment.BottomCenter,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
//        TODO: set min size depends on longest option name
        val thumbnailSize = 70.dp

        LazyRow {
            items(photoOptions) { option ->
                OptionCard(
                    modifier = modifier
                        .width(thumbnailSize)
                        .aspectRatio(1f),
                    imageBitmap = option.first.thumbnail?.asImageBitmap(),
                    isSelected = currentSelectedId == option.first.nameId,
                    title = stringResource(id = option.first.nameId),
                    onClick = { option.second.invoke() }
                )
            }
        }
    }
}