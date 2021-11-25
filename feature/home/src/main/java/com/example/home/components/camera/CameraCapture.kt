package com.example.home.components.camera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore.EXTRA_OUTPUT
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext


@Composable
fun CameraCapture(
    onImageUri: (Uri) -> Unit = { },
) {
    val context = LocalContext.current

    val imageUri = saveMediaToStorage(context)

    val launcher = rememberLauncherForActivityResult(
        contract = object : ActivityResultContracts.TakePicturePreview() {
            override fun createIntent(context: Context, input: Void?): Intent {
                return super.createIntent(context, input).putExtra(EXTRA_OUTPUT, imageUri)
            }
        }

    ) {
        onImageUri(imageUri)
    }

    @Composable
    fun LaunchCamera() {
        SideEffect {
            launcher.launch()
        }
    }

    LaunchCamera()
}