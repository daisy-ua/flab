package flab.editor.library.adjust.filters

import org.opencv.core.CvType
import org.opencv.core.Mat

object KernelMats {
    val SEPIA_MAT = Mat(3, 4, CvType.CV_32F).also {
        it.put(0, 0, FilterKernels.SEPIA_DATA)
    }

    val COLORED_MAT = Mat(3, 4, CvType.CV_32F).also {
        it.put(0, 0, FilterKernels.COLOR_DATA)
    }
}