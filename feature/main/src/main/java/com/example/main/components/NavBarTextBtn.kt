package com.example.main.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun NavBarTextBtn(
    text: String,
    fontSize: TextUnit = 20.sp,
    modifier: Modifier,
    onCLick: () -> Unit = { },
) {
    Text(
        text = text,
        fontSize = fontSize,
        modifier = modifier.clickable(onClick = onCLick)
    )
}