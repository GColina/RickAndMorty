package com.gcolina.rickandmorty.presentation.detail.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gcolina.rickandmorty.presentation.detail.viewModel.DetailViewModel

@Composable
fun DetailScreen(detailViewModel: DetailViewModel = hiltViewModel(), onBackPressed: () -> Boolean) {

    val uiState by detailViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        detailViewModel.getCharacterById()
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            onBackPressed()
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(context).scale(scale = coil.size.Scale.FILL)
                .data(uiState.character?.image).crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop,
        )
        Text(text = uiState.character?.name ?: "")

        Text(text = uiState.character?.episodeCount.toString())

    }

}