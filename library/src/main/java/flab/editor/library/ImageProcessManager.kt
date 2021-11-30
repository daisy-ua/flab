package flab.editor.library

import android.graphics.Bitmap
import flab.editor.library.adjust.HSVTransform
import flab.editor.library.adjust.Sharpness
import flab.editor.library.adjust.Tune

class ImageProcessManager(private val bitmap: Bitmap) {
    //TODO:convert all to  : Mat
    val tune: Tune by lazy { Tune(bitmap) }
    val sharpness: Sharpness by lazy { Sharpness(bitmap) }
    val hsvTransform: HSVTransform by lazy { HSVTransform(bitmap) }

    fun updateSource(
        contrast: Double?,
        brightness: Double?,
        hue: Double?,
        saturation: Double?,
        value: Double?,
    ): Bitmap {
        tune.updateSource(bitmap)
        hsvTransform.updateSource(bitmap)

        var draft = contrast?.let { _contrast ->
            brightness?.let { _brightness ->
                tune.setBrightnessContrast(_contrast, _brightness)
            }
        }

        draft?.let { _draft ->
            hsvTransform.updateSource(_draft)
        }

        if (hue != null && saturation != null && value != null) {
            draft = hsvTransform.setHSVTransform(hue, saturation, value)
        }


        return draft ?: bitmap
    }


}