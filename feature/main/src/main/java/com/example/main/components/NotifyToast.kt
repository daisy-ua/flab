package com.example.main.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.main.R

@Composable
fun NotifyToast(
    context: Context,
    saveTest: String = stringResource(id = R.string.saved_gallery),
) {
    Toast.makeText(
        context,
        saveTest,
        Toast.LENGTH_SHORT
    ).show()
}