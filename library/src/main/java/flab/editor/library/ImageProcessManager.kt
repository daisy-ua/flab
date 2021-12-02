package flab.editor.library

import android.graphics.Bitmap
import com.example.constants.FlipAlignment
import com.example.constants.RotateAlignment
import flab.editor.library.adjust.*

class ImageProcessManager(private val bitmap: Bitmap) {
    //TODO:convert all to  : Mat
    var tune: Tune = Tune(bitmap)
    var sharpness: Sharpness = Sharpness(bitmap)
    var hsvTransform: HSVTransform = HSVTransform(bitmap)
    val blend: Blend by lazy { Blend(bitmap) }
    var flipRotate = FlipRotate(bitmap)

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

        sharpness.updateSource(source!!)

        var draft = applySharpness(sharpnessValue)

        return draft ?: bitmap
    }

    fun applySharpness(sharpnessValue: Double?) : Bitmap? {
        return sharpnessValue?.let { _sharpness ->
            if (_sharpness > 0)
                sharpness.sharpen(_sharpness)
            else
                sharpness.blur(-_sharpness)
        }
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
        return if (hue == null || saturation == null || value == null) null
        else hsvTransform.setHSVTransform(hue, saturation, value)
    }

    fun applyRotate() = flipRotate.rotate(RotateAlignment.RIGHT)

    fun applyFlip() = flipRotate.flip(FlipAlignment.Y_AXIS)

    fun setSource(bitmap: Bitmap) {
        tune = Tune(bitmap)
        sharpness = Sharpness(bitmap)
        hsvTransform = HSVTransform(bitmap)
        flipRotate = FlipRotate(bitmap)
    }

    fun updateTune(bitmap: Bitmap) = tune.updateSource(bitmap)
    fun updateFlipRotate(bitmap: Bitmap) = flipRotate.updateSource(bitmap)

    fun applySourceMerge(
        src1: Bitmap,
        src2: Bitmap,
        opacity: Double = 0.5,
    ) = blend.blend(src1, src2)

}