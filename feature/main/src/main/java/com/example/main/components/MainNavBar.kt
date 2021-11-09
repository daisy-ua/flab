package com.example.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.main.R

@Composable
fun MainNavTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(start = 20.dp, end = 20.dp)
    ) {
        NavBarTextBtn(
            text = stringResource(id = R.string.open_image),
            modifier = Modifier.align(Alignment.CenterStart)
        )

        NavBarTextBtn(
            text = stringResource(id = R.string.save_image),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainNavTopBar()
}
