package com.example.main.models

import com.example.main.R

sealed class FirstLevelOptions : IPhotoOption {
    object TuneOption : FirstLevelOptions() {
        override val nameId: Int
            get() = R.string.tune_option
        override val drawableId: Int
            get() = R.drawable.ic_tune_24
    }

    object Clarity : FirstLevelOptions() {
        override val nameId: Int
            get() = R.string.sharpness_option
        override val drawableId: Int
            get() = R.drawable.ic_details_24
    }

    object FlipRotateOption : FirstLevelOptions() {
        override val nameId: Int
            get() = R.string.flip_rotate_option
        override val drawableId: Int
            get() = R.drawable.ic_rotate_right_24
    }

    object ColorOption : FirstLevelOptions() {
        override val nameId: Int
            get() = R.string.color_option
        override val drawableId: Int
            get() = R.drawable.ic_color_24
    }

    object EffectOption : FirstLevelOptions() {
        override val nameId: Int
            get() = R.string.effect_option
        override val drawableId: Int
            get() = R.drawable.ic_effect_24
    }
}