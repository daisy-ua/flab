package com.example.main.models

import com.example.main.R
import com.example.main.components.options.IPhotoOption

sealed class FlipRotateOptions : IPhotoOption {
    object FlipOption : FlipRotateOptions() {
        override val nameId: Int
            get() = R.string.flip_option
        override val drawableId: Int
            get() = R.drawable.ic_flip_24
    }

    object RotateOption : FlipRotateOptions() {
        override val nameId: Int
            get() = R.string.rotate_option
        override val drawableId: Int
            get() = R.drawable.ic_rotate_right_24
    }
}