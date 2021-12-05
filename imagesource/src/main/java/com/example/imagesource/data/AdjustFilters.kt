package com.example.imagesource.data

sealed class AdjustFilters {
    object LinearFilters : AdjustFilters()

    object HSVFilters : AdjustFilters()
}