package com.example.main.ui.options.utils

import android.graphics.Bitmap
import flab.editor.library.ImageProcessManager

interface ILayerProcessManager : IProcessManager {
    suspend fun setup(
        processManager: ImageProcessManager,
        source: Bitmap?
    )
}