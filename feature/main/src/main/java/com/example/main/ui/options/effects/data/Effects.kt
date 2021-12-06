package com.example.main.ui.options.effects.data

import android.graphics.Bitmap
import com.example.main.R

sealed class Effects(
    val nameId: Int,
    var thumbnail: Bitmap? = null,
) {
    object Original : Effects(nameId = R.string.original_effect)

    object GrayScale : Effects(nameId = R.string.grayscale_effect)

    object Binary : Effects(nameId = R.string.binary_effect)

    object Otsu : Effects(nameId = R.string.otsu_effect)

    object Sepia : Effects(nameId = R.string.sepia_effect)

    object Colored : Effects(nameId = R.string.colored_effect)

    object Winter: Effects(nameId = R.string.winter_effect)
}
