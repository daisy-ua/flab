package com.example.main.ui.options.effects

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.ui.options.effects.data.Cache
import com.example.main.ui.options.effects.data.Effects
import com.example.main.ui.options.utils.IProcessManager
import flab.editor.library.ImageProcessManager
import flab.editor.library.adjust.filters.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EffectViewModel : ViewModel(), IProcessManager {
    var source by mutableStateOf<Bitmap?>(null)

    override lateinit var processManager: ImageProcessManager
    private lateinit var mode: Filters

    private var cache: Cache? = null
    var appliedEffect: Effects? = Effects.Original
        private set

    val photoOptions = mutableListOf<Pair<Effects, () -> Any>>()

    suspend fun setup(
        processManager: ImageProcessManager,
        source: Bitmap?,
    ) {
        mode = Filters(source!!)
        setupProcessor(processManager)
        this.source = source
        getEffectThumbnails()
    }

    private fun setFilter(filterId: Int) = viewModelScope.launch {
        source = applyFilter(filterId)
    }

    private suspend fun applyFilter(filterId: Int, mode: Filters = this.mode) =
        withContext(Dispatchers.Default) {
            return@withContext if (cache != null && filterId == cache?.filterNameId) {
                cache!!.cache
            } else {
                val filter = getFilter(filterId, mode)
                val src = withContext(Dispatchers.Default) { filter.invoke() }
                cache(filterId, src)
                src
            }
        }

    suspend fun getBitmapForSave(source: Bitmap) =
        withContext(viewModelScope.coroutineContext) {
            appliedEffect?.let { appliedEffect ->
                val effect = Filters(source)
                return@withContext applyFilter(appliedEffect.nameId, effect)
            }
        }

    private fun cache(filterId: Int, bitmap: Bitmap) {
        cache = Cache(filterNameId = filterId, cache = bitmap)
    }

    private suspend fun getEffectThumbnails() {
        withContext(Dispatchers.Default) {
            val thumbnailBitmap = getThumbnailSource()
            thumbnailBitmap?.let { bitmap ->
                val filter = Filters(bitmap)
                val effects = getDefaultEffectList()

                effects.forEach { effect ->
                    val (thumbnail, callback) = when (effect.nameId) {
                        Effects.GrayScale.nameId ->
                            Pair(filter.applyGrayScale(), { setFilter(effect.nameId) })

                        Effects.Sepia.nameId ->
                            Pair(filter.applySepia(), { setFilter(effect.nameId) })

                        Effects.Binary.nameId ->
                            Pair(filter.applyBinary(), { setFilter(effect.nameId) })

                        Effects.Otsu.nameId ->
                            Pair(filter.applyOtsu(), { setFilter(effect.nameId) })

                        Effects.Colored.nameId ->
                            Pair(filter.applyColored(), { setFilter(effect.nameId) })

                        Effects.Winter.nameId ->
                            Pair(filter.applyWinter(), { setFilter(effect.nameId) })

                        Effects.Pink.nameId ->
                            Pair(filter.applyPink(), { setFilter(effect.nameId) })

                        Effects.Cyan.nameId ->
                            Pair(filter.applyCyan(), { setFilter(effect.nameId) })

                        Effects.Graphite.nameId ->
                            Pair(filter.applyGraphite(), { setFilter(effect.nameId) })

                        Effects.Old.nameId ->
                            Pair(filter.applyOld(), { setFilter(effect.nameId) })

                        else -> Pair(bitmap, { source = mode.getSource() })
                    }

                    effect.thumbnail = thumbnail
                    photoOptions.add(Pair(effect, {
                        appliedEffect = effect
                        callback()
                    }))
                }
            }
        }
    }

    private suspend fun getThumbnailSource(): Bitmap? {
        return withContext(Dispatchers.Default) {
            source?.let {
                val bitmap = getSquareBitmap(it)

                Bitmap.createScaledBitmap(
                    bitmap, 150, 150, false
                )
            }
        }
    }

    private fun getSquareBitmap(bitmap: Bitmap) : Bitmap {
        return if (bitmap.width >= bitmap.height) {
            Bitmap.createBitmap(
                bitmap,
                bitmap.width / 2 - bitmap.height / 2,
                0,
                bitmap.height,
                bitmap.height
            )
        } else {
            Bitmap.createBitmap(
                bitmap,
                0,
                bitmap.height / 2 - bitmap.width / 2,
                bitmap.width,
                bitmap.width
            )
        }
    }

    private fun getDefaultEffectList() = listOf(
        Effects.Original,
        Effects.Sepia,
        Effects.Winter,
        Effects.Pink,
        Effects.Cyan,
        Effects.Colored,
        Effects.Graphite,
        Effects.Old,
        Effects.Binary,
        Effects.Otsu,
        Effects.GrayScale,
    )

    private fun getFilter(nameId: Int, mode: Filters = this.mode): () -> Bitmap {
        return when (nameId) {
            Effects.GrayScale.nameId -> mode::applyGrayScale

            Effects.Sepia.nameId -> mode::applySepia

            Effects.Binary.nameId -> mode::applyBinary

            Effects.Otsu.nameId -> mode::applyOtsu

            Effects.Colored.nameId -> mode::applyColored

            Effects.Winter.nameId -> mode::applyWinter

            Effects.Cyan.nameId -> mode::applyCyan

            Effects.Pink.nameId -> mode::applyPink

            Effects.Graphite.nameId -> mode::applyGraphite

            Effects.Old.nameId -> mode::applyOld

            else -> mode::getSource
        }
    }
}