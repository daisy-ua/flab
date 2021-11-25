package com.example.main.models

import com.example.main.R
import com.example.main.components.options.IPhotoOption

sealed class ColorScreenOptions : IPhotoOption {
    object HueOption : ColorScreenOptions() {
        override val nameId: Int
            get() = R.string.hue_option
        override val drawableId: Int
            get() = R.drawable.ic_hue_2
    }

    object SaturationOption : ColorScreenOptions() {
        override val nameId: Int
            get() = R.string.saturation_option
        override val drawableId: Int
            get() = R.drawable.ic_saturation_24
    }

    object ValueOption : ColorScreenOptions() {
        override val nameId: Int
            get() = R.string.value_option
        override val drawableId: Int
            get() = R.drawable.ic_brightness_24_1_
    }
}