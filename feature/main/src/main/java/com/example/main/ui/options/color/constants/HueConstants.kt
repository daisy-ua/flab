package com.example.main.ui.options.color.constants

import com.example.main.constants.SliderConstants

object HueConstants : SliderConstants {
    override val min: Float
        get() = -180f
    override val max: Float
        get() = 180f
    override val mean: Float
        get() = 0f
}