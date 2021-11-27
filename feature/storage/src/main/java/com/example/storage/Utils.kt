package com.example.storage

import android.content.Context
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

internal fun createFileName(context: Context): String {
    val formatPattern = context.resources.getString(R.string.datetime_format_pattern)
    val timeStamp: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        val current = LocalDateTime.now()
        current.format(formatter)
    } else {
        val current = SimpleDateFormat(formatPattern, Locale("US"))
        current.format(Date())
    }

    return "${context.resources.getString(R.string.project_name)}-$timeStamp.jpg"
}