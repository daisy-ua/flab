package com.example.main.ui.options.color.constants

import com.example.main.constants.SliderConstants

object SaturationConstants : SliderConstants {
    override val min: Float
        get() = -255f
    override val max: Float
        get() = 150f
    override val mean: Float
        get() = 0f
}