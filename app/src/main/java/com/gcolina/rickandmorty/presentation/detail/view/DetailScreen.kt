package com.gcolina.rickandmorty.presentation.detail.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gcolina.rickandmorty.R
import com.gcolina.rickandmorty.presentation.common.Loader
import com.gcolina.rickandmorty.presentation.detail.viewModel.DetailViewModel

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navigateToError: () -> Unit,
    onBackPressed: () -> Boolean
) {

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

    Loader(isLoading = uiState.isLoading, isApiError = false, navigateToError = {
        navigateToError()
    }) {
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
            Column(modifier = Modifier.padding(dimensionResource(R.dimen.mid_padding))) {
                Card() {
                    Text(text = uiState.character?.name ?: "")
                    Text(text = uiState.character?.episodeCount.toString())
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(uiState.character?.episodeList?.size ?: 0) { position ->
                        Text(text = uiState.character?.episodeList?.get(position) ?: "")
                    }
                }
            }


        }
    }
}

