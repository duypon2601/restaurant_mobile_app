package com.example.restaurant_mobile_app.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import com.example.restaurant_mobile_app.R
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun getFirebaseImageUrl(path: String): String? = suspendCancellableCoroutine { cont ->
    val storageRef = FirebaseStorage.getInstance().reference.child(path)
    storageRef.downloadUrl
        .addOnSuccessListener { uri -> cont.resume(uri.toString()) }
        .addOnFailureListener { cont.resume(null) }
}

@Composable
fun FirebaseImage(
    storagePath: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(storagePath) {
        if (storagePath.isNullOrBlank()) {
            imageUrl = null
            isLoading = false
        } else if (storagePath.startsWith("http")) {
            imageUrl = storagePath
            isLoading = false
        } else {
            isLoading = true
            imageUrl = getFirebaseImageUrl(storagePath)
            isLoading = false
        }
    }

    if (isLoading) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        if (imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
} 