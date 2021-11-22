package com.example.main.models

import com.example.main.R
import com.example.main.components.options.IPhotoOption

sealed class FirstLevelOptions : IPhotoOption {
    object TuneOption : FirstLevelOptions() {
        override val nameId: Int
            get() = R.string.tune_option
        override val drawableId: Int
            get() = R.drawable.ic_tune_24
    }
}