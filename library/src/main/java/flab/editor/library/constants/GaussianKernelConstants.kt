package flab.editor.library.constants

import flab.editor.library.constants.SliderConstants

object GaussianKernelConstants : SliderConstants {
    override val min: Float
        get() = -100f
    override val max: Float
        get() = 100f
    override val mean: Float
        get() = 0f
}