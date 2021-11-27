package com.example.home.components.camera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore.EXTRA_OUTPUT
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.storage.IOProcesses


@Composable
fun CameraCapture(
    onImageUri: (Uri) -> Unit = { },
) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(key1 = false) {
        imageUri = IOProcesses.saveEmptyMediaToStorage(context)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = object : ActivityResultContracts.TakePicturePreview() {
            override fun createIntent(context: Context, input: Void?): Intent {
                return super.createIntent(context, input).putExtra(EXTRA_OUTPUT, imageUri)
            }
        }

    ) {
        imageUri?.let { uri ->
            onImageUri(uri)
        }
    }

    @Composable
    fun LaunchCamera() {
        imageUri?.let {
            SideEffect {
                launcher.launch()
            }
        }
    }

    LaunchCamera()
}