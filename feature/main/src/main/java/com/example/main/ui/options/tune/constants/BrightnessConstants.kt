package com.example.main.ui.options.tune.constants

import com.example.main.constants.SliderConstants

object BrightnessConstants : SliderConstants {
    override val min: Float
        get() = -100f
    override val max: Float
        get() = 100f
    override val mean: Float
        get() = 0f
}