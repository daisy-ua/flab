package com.example.main.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.main.components.options.IPhotoOption

@Composable
fun OptionsBottomBar(
    modifier: Modifier = Modifier,
    photoOptions: List<Pair<IPhotoOption, () -> Unit>>,
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
                    drawableId = option.first.drawableId,
                    titleId = option.first.nameId,
                    onClick = option.second
                )
            }
        }
    }

}