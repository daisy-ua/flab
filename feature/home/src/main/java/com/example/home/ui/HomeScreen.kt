package com.example.home.ui

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.components.DefaultScreenUI
import com.example.home.R
import com.example.home.components.camera.CameraCapture
import com.example.home.components.gallery.GallerySelect
import com.example.home.constants.EMPTY_IMAGE_URI
import com.example.home.utils.RequestPermission

@Composable
fun HomeScreen(
    logoId: Int,
    navigateToMainScreen: (String) -> Unit,
) {
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
    if (imageUri != EMPTY_IMAGE_URI)
        LaunchedEffect(Unit) {
            val uriEncoded = Uri.encode(imageUri.toString())
            navigateToMainScreen(uriEncoded)
        }

    var showGallerySelect by remember { mutableStateOf(false) }
    if (showGallerySelect) {
        GallerySelect(
            onImageUri = { uri: Uri ->
                showGallerySelect = false
                imageUri = uri
            }
        )
    }

    var showCameraCapture by remember { mutableStateOf(false) }
    if (showCameraCapture) {
        RequestPermission(
            permission = Manifest.permission.CAMERA,
            onClose = { showCameraCapture = false }
        ) {
            CameraCapture(
                onImageUri = { uri ->
                    showCameraCapture = false
                    imageUri = uri
                }
            )
        }
    }

    DefaultScreenUI {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(logoId),
                contentDescription = stringResource(R.string.logo_alt),
                modifier = Modifier
                    .width(150.dp)
                    .align(Alignment.TopCenter)
                    .absolutePadding(top = 50.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp, 45.dp),
                verticalArrangement = Arrangement.Absolute.spacedBy(10.dp)
            ) {
                FloatingActionButton(
                    onClick = { showCameraCapture = true },
                    modifier = Modifier.scale(0.7f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera_alt_24),
                        contentDescription = stringResource(R.string.ic_add_alt),
                        tint = Color.White
                    )
                }

                FloatingActionButton(
                    onClick = { showGallerySelect = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_24),
                        contentDescription = stringResource(R.string.ic_add_alt),
                        tint = Color.White
                    )
                }

            }
        }
    }
}