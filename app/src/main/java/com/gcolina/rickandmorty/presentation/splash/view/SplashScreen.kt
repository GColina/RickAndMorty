package com.gcolina.rickandmorty.presentation.splash.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.gcolina.rickandmorty.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToHome: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(3000)
        navigateToHome()
    }

    Box(
        modifier = Modifier
            .background(colorResource(R.color.primary_color))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo_rickandmorty),
            contentDescription = null,
            modifier = Modifier.padding(
                dimensionResource(R.dimen.big_padding)
            )
        )
    }
}