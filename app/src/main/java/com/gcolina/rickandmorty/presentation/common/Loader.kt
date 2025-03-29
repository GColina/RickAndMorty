package com.gcolina.rickandmorty.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.gcolina.rickandmorty.R

@Composable
fun Loader(
    isLoading: Boolean,
    isApiError: Boolean,
    navigateToError: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary_color)),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = colorResource(R.color.tertiary_color)
            )
        } else if (isApiError) {
            navigateToError()
        } else content()
    }
}