package com.example.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.main.R
import com.example.main.constants.SliderRange


@Composable
fun AmountSlider(
    modifier: Modifier = Modifier,
    sliderValue: Float,
    valueRange:  ClosedFloatingPointRange<Float> = SliderRange.MIN_SLIDER..SliderRange.MAX_SLIDER,
    onValueChange: (Float) -> Unit = { },
    onValueChangeFinished: () -> Unit = { }
) {
    Row(modifier) {
        Text(text = stringResource(id = R.string.amount))

        Slider(
            value = sliderValue,
            onValueChange = onValueChange,
            valueRange = valueRange,
            onValueChangeFinished = onValueChangeFinished
        )
    }
}
