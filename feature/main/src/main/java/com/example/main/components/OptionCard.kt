package com.example.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.main.R


@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    imageBitmap: ImageBitmap? = null,
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = { },
) {
    val color = if (isSelected) colorResource(id = R.color.seance) else Color.Gray

    Box(
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (painter != null) {
                Image(painter = painter,
                    contentDescription = title,
                    colorFilter = ColorFilter.tint(color))
            } else if (imageBitmap != null) {
                Image(bitmap = imageBitmap, contentDescription = title)
            }
            Text(text = title, fontSize = 12.sp, color = color)
        }
    }
}