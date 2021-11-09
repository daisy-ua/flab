package com.example.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OptionCard(
    modifier: Modifier = Modifier,
    drawableId: Int,
    titleId: Int,
    onClick: () -> Unit = { },
) {
    val title = stringResource(titleId)

    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title)
            
            Image(painter = painterResource(id = drawableId), contentDescription = title)
        }
    }
}