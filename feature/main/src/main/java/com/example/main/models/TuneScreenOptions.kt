package com.example.main.models

import com.example.main.R
import com.example.main.components.options.IPhotoOption

sealed class TuneScreenOptions : IPhotoOption {
    object Contrast : TuneScreenOptions() {
        override val nameId: Int
            get() = R.string.contrast_option
        override val drawableId: Int
            get() = R.drawable.ic_contrast_24
    }
    object Brightness : TuneScreenOptions() {
        override val nameId: Int
            get() = R.string.brightness_option
        override val drawableId: Int
            get() = R.drawable.ic_brightness_24
    }
    object Blur : TuneScreenOptions() {
        override val nameId: Int
            get() = R.string.brightness_option
        override val drawableId: Int
            get() = R.drawable.ic_brightness_24
    }
    object Sharpening : TuneScreenOptions() {
        override val nameId: Int
            get() = R.string.sharpness_option
        override val drawableId: Int
            get() = R.drawable.ic_details_24
    }
}