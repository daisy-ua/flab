package com.example.main.ui.options.utils

import android.graphics.Bitmap
import flab.editor.library.ImageProcessManager

interface IProcessManager {
    var processManager: ImageProcessManager

    val originalSource: Bitmap get() = processManager.originalSource

    fun setupProcessor(processManager: ImageProcessManager) {
        this.processManager = processManager
    }
}