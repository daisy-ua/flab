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

    val PINK_MAT = Mat(3, 4, CvType.CV_32F).also {
        it.put(0, 0, FilterKernels.PINK_DATA)
    }

    val CYAN_MAT = Mat(3, 4, CvType.CV_32F).also {
        it.put(0, 0, FilterKernels.BLUE_DATA)
    }

    val BLUE_MAT = Mat(3, 4, CvType.CV_32F).also {
        it.put(0, 0, FilterKernels.WINTER_DATA)
    }

    val GRAPHITE_MAT = Mat(3, 4, CvType.CV_32F).also {
        it.put(0, 0, FilterKernels.GRAPHITE_DATA)
    }

    val OLD_MAT = Mat(3, 4, CvType.CV_32F).also {
        it.put(0, 0, FilterKernels.OLD_DATA)
    }
}