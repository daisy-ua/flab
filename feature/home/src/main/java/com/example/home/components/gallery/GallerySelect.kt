package com.example.home.components.gallery

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.example.home.constants.EMPTY_IMAGE_URI

@Composable
fun GallerySelect(
    onImageUri: (Uri) -> Unit = { }
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageUri(uri ?: EMPTY_IMAGE_URI)
    }

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch("image/*")
        }
    }

    LaunchGallery()
}

