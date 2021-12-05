package flab.editor.library

import android.graphics.Bitmap
import com.example.constants.FlipAlignment
import com.example.constants.RotateAlignment
import flab.editor.library.adjust.*

class ImageProcessManager(bitmap: Bitmap) {
    private var tune: Tune = Tune(bitmap)
    private var sharpness: Sharpness = Sharpness(bitmap)
    private var hsvTransform: HSVTransform = HSVTransform(bitmap)
    private var flipRotate = FlipRotate(bitmap)

    var originalSource: Bitmap = bitmap
        private set

    fun setNewOriginalSource(bitmap: Bitmap) {
        originalSource = bitmap
    }

    fun applySharpness(sharpnessValue: Double?, source: Bitmap? = null) : Bitmap? {
        source?.let {
            sharpness = Sharpness(source)
        }
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
        source?.let { hsvTransform = HSVTransform(source) }
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
    fun updateSharpness(bitmap: Bitmap?) = bitmap?.let { sharpness.updateSource(it) }
}