package com.example.main.components.options

import com.example.main.R

sealed class FirstLevelOptions : IPhotoOption {
    object TuneOption : FirstLevelOptions() {
        override val nameId: Int
            get() = R.string.tune_option
        override val drawableId: Int
            get() = R.drawable.ic_tune_24
    }
}