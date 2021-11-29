package flab.editor.library.constants

import flab.editor.library.constants.SliderConstants

object HueConstants : SliderConstants {
    override val min: Float
        get() = -180f
    override val max: Float
        get() = 180f
    override val mean: Float
        get() = 0f
}