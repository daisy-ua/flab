package com.example.main.ui.options.effects

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.ui.options.IProcessManager
import com.example.main.ui.options.effects.data.Cache
import com.example.main.ui.options.effects.data.Effects
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

    private fun setFilter(filterId: Int, filter: () -> Bitmap) = viewModelScope.launch {
        source = applyFilter(filterId, filter)
    }

    private suspend fun applyFilter(filterId: Int, filter: () -> Bitmap) =
        withContext(Dispatchers.Default) {
            return@withContext if (cache != null && filterId == cache?.filterNameId) {
                cache!!.cache
            } else {
                val src = withContext(Dispatchers.Default) { filter.invoke() }
                cache(filterId, source!!)
                src
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
                        Effects.GrayScale.nameId -> Pair(filter.applyGrayScale(),
                            { setFilter(effect.nameId, mode::applyGrayScale) })

                        Effects.Sepia.nameId -> Pair(filter.applySepia(),
                            { setFilter(effect.nameId, mode::applySepia) })

                        Effects.Binary.nameId -> Pair(filter.applyBinary(),
                            { setFilter(effect.nameId, mode::applyBinary) })

                        Effects.Otsu.nameId -> Pair(filter.applyOtsu(),
                            { setFilter(effect.nameId, mode::applyOtsu) })

                        Effects.Colored.nameId -> Pair(filter.applyColored(),
                            { setFilter(effect.nameId, mode::applyColored) })

                        else -> Pair(bitmap, { source = mode.getSource() })
                    }

                    effect.thumbnail = thumbnail
                    photoOptions.add(Pair(effect, callback))
                }
            }
        }
    }

    private suspend fun getThumbnailSource(): Bitmap? {
        return withContext(Dispatchers.Default) {
            source?.let {
                Bitmap.createScaledBitmap(
                    it,
                    100,
                    it.height * 100 / it.width,
                    false
                )
            }
        }
    }

    private fun getDefaultEffectList() = listOf(
        Effects.Original,
        Effects.Sepia,
        Effects.Colored,
        Effects.Binary,
        Effects.Otsu,
        Effects.GrayScale,
    )
}