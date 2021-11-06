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
//    val context = LocalContext.current
//
//    val imageUri by remember {
//        mutableStateOf<Uri?>(null)
//    }
//
//    val bitmap by remember {
//        mutableStateOf<Bitmap?>(null)
//    }
//
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

    //TODO: ask for permission
    LaunchGallery()
//
//    imageUri?.let { uri ->
//        if (Build.VERSION.SDK_INT < 28) {
//            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//        }
//        else {
//            val source = ImageDecoder.createSource(context.contentResolver, uri)
//            bitmap.value = ImageDecoder.decodeBitmap(source)
//        }
//    }

//    bitmap.value?.let { btm ->
//        Log.i("rita", "navigate")
////        navigateToMainScreen("image string")
//    }

}

