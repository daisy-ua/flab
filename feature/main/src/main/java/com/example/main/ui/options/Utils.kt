package com.example.main.ui.options

import com.example.main.constants.SliderConstants

internal fun convertPercentageToValue(
    x: Float,
    constants: SliderConstants,
): Double =
    when {
        x == 0f -> constants.mean
        x < 0 -> x * (constants.mean - constants.min) / 100 + constants.mean
        else -> x * (constants.max - constants.mean) / 100 + constants.mean
    }.toDouble()

internal fun convertValueToPercentage(
    x: Float,
    constants: SliderConstants,
): Float =
    when {
        x == constants.mean -> 0f
        x < constants.mean -> (x - constants.mean) / (constants.mean - constants.min) * 100
        else -> (x - constants.mean) / (constants.max - constants.mean) * 100
    }.toInt().toFloat()
