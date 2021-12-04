package com.example.main.ui.options

import android.graphics.Bitmap
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction0

interface ILayerProcessManager : IProcessManager {
    suspend fun setup(
        processManager: ImageProcessManager,
        getInitialHSVBitmap: KSuspendFunction0<Bitmap?>,
        getInitialTuneBitmap: KSuspendFunction0<Bitmap?>,
    )

    suspend fun mergeSource(src1: Bitmap, src2: Bitmap) =
        withContext(Dispatchers.Default) {
            processManager.applySourceMerge(src1, src2)
        }

    suspend fun setInitialSource(): Bitmap
}