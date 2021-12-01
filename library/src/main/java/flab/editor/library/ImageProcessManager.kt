package flab.editor.library

import android.graphics.Bitmap
import flab.editor.library.adjust.Blend
import flab.editor.library.adjust.HSVTransform
import flab.editor.library.adjust.Sharpness
import flab.editor.library.adjust.Tune

class ImageProcessManager(private val bitmap: Bitmap) {
    //TODO:convert all to  : Mat
    val tune: Tune by lazy { Tune(bitmap) }
    val sharpness: Sharpness by lazy { Sharpness(bitmap) }
    val hsvTransform: HSVTransform by lazy { HSVTransform(bitmap) }
    val blend: Blend by lazy { Blend(bitmap) }

    var originalSource: Bitmap = bitmap
        private set

    fun setNewOriginalSource(bitmap: Bitmap) {
        originalSource = bitmap
    }

    fun updateSource(
        contrast: Double?,
        brightness: Double?,
        hue: Double?,
        saturation: Double?,
        value: Double?,
        sharpnessValue: Double?,
        source: Bitmap? = null,
    ): Bitmap {
        tune.updateSource(source!!)
        sharpness.updateSource(source)
        hsvTransform.updateSource(source)

        var draft = sharpnessValue?.let { _sharpness ->
            if (_sharpness > 0)
                sharpness.sharpen(_sharpness)
            else
                sharpness.blur(-_sharpness)
        }

        tune.updateSource(bitmap)
        sharpness.updateSource(bitmap)
        hsvTransform.updateSource(bitmap)

        return draft ?: bitmap
    }


    fun applyLinearTransform(alpha: Double?, beta: Double?, source: Bitmap? = null): Bitmap? {
        source?.let { tune.updateSource(source) }
        return if (alpha == null || beta == null) null
        else tune.setBrightnessContrast(alpha, beta)

    }

    fun applyHSVTransform(
        hue: Double?,
        saturation: Double?,
        value: Double?,
        source: Bitmap? = null,
    ): Bitmap? {
        source?.let { hsvTransform.updateSource(source) }
        return if (hue == null || saturation == null || value == null) null
        else hsvTransform.setHSVTransform(hue, saturation, value)
    }

}