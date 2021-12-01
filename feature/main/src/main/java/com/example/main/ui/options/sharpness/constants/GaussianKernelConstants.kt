package com.example.main.ui.options.sharpness.constants

import com.example.main.constants.SliderConstants

object GaussianKernelConstants : SliderConstants {
    override val min: Float
        get() = -100f
    override val max: Float
        get() = 100f
    override val mean: Float
        get() = 0f
}