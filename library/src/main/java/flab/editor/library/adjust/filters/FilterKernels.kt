package flab.editor.library.adjust.filters

object FilterKernels {
    val SEPIA_DATA = floatArrayOf(
        0f, 0.393f, 0.769f, 0.189f,
        0f, 0.349f, 0.686f, 0.168f,
        0f, 0.272f, 0.534f, 0.131f
    )

    val COLOR_DATA = floatArrayOf(
        0f, 1.6321f, -0.6964f, 0.0393f,
        0f, -0.3313f, 1.5779f, -0.2277f,
        0f, 0.0061f, -0.8786f, 1.8786f
    )

    val PINK_DATA = floatArrayOf(
        0f, 0.393f, 0.769f, 0.189f,
        0f, 0.272f, 0.534f, 0.131f,
        0f, 0.349f, 0.686f, 0.168f
    )

    val BLUE_DATA = floatArrayOf(
        0f, 0.272f, 0.534f, 0.131f,
        0f, 0.349f, 0.686f, 0.168f,
        0f, 0.393f, 0.769f, 0.189f
    )

    val GRAPHITE_DATA = floatArrayOf(
        0f, 0.168f, 0.1f, 0.3f,
        0f, 0.2f, 0.2f, 0.203f,
        0f, 0.286f, 0.506f, 0.189f
    )

    val OLD_DATA = floatArrayOf(
        0f, 0.4321f, 0.6964f, 0.0393f,
        0f, 0.3313f, 0.5779f, 0.2277f,
        0f, 0.0061f, 0.5786f, 0.2786f
    )

    val WINTER_DATA = floatArrayOf(
        0f, 0.0393f, 0.6964f, 1.6321f,
        0f, 0.3313f, 0.5779f, 0.2277f,
        0f, 0.8786f, 0.8786f, 0.0061f
    )
}