package com.example.main.ui.options.tune.constants

import com.example.main.constants.SliderConstants

object ContrastConstants : SliderConstants {
    override val min: Float
        get() = 0.5f
    override val max: Float
        get() = 3f
    override val mean: Float
        get() = 1f
}