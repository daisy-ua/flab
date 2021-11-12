package flab.editor.library.adjust

import android.graphics.Bitmap
import com.example.constants.FlipAlignment
import com.example.constants.RotateAlignment
import flab.editor.library.ImageProcessing
import org.opencv.core.Core

class FlipRotate(
    bitmap: Bitmap
) : ImageProcessing(bitmap) {

    fun flip(axis: FlipAlignment) : Bitmap {
        Core.flip(src, src, axis.code)
        createResultBitmap()
        saveResult()
        return result
    }

    fun rotate(direction: RotateAlignment) : Bitmap {
        Core.transpose(src, src)
        return flip(direction.code)
    }
}