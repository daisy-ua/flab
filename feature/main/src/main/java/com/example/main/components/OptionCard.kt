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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp


@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    drawableId: Int,
    titleId: Int,
    onClick: () -> Unit = { },
) {
    val title = stringResource(titleId)

    Box(
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = drawableId), contentDescription = title)
            Text(text = title, fontSize = 12.sp)
        }
    }
}