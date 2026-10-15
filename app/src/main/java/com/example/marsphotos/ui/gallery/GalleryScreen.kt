package com.example.marsphotos.ui.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.marsphotos.R

private val galleryImages = listOf(
    R.drawable.gallery_placeholder_1,
    R.drawable.gallery_placeholder_2,
    R.drawable.gallery_placeholder_3,
)

@Composable
fun GalleryScreen(onTouch: () -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { onTouch() }
            }
    ) {
        items(galleryImages) { imageRes ->
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
