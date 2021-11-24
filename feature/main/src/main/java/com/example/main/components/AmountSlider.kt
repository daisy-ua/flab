package com.example.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.main.R
import com.example.main.constants.SliderRange


@Composable
fun AmountSlider(
    modifier: Modifier = Modifier,
    sliderName: Int = R.string.amount,
    sliderValue: Float,
    valueRange:  ClosedFloatingPointRange<Float> = SliderRange.MIN_SLIDER..SliderRange.MAX_SLIDER,
    onValueChange: (Float) -> Unit = { },
    onValueChangeFinished: () -> Unit = { }
) {
    Row(modifier.fillMaxWidth().padding(16.dp, 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = sliderName))

        Slider(
            value = sliderValue,
            onValueChange = onValueChange,
            valueRange = valueRange,
            onValueChangeFinished = onValueChangeFinished
        )
    }
}
