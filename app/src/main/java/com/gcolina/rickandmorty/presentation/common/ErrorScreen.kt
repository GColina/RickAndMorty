package com.gcolina.rickandmorty.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.gcolina.rickandmorty.R

@Composable
fun ErrorScreen(navigateToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.primary_color))
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.big_padding))
    ) {
        Image(painter = painterResource(R.drawable.ic_logo_rickandmorty), null)

        Spacer(Modifier.height(dimensionResource(R.dimen.big_padding)))

        Text("Glops!")

        Spacer(Modifier.height(dimensionResource(R.dimen.big_padding)))

        Text("Page not found")

        Spacer(Modifier.height(dimensionResource(R.dimen.big_padding)))

        CustomButton(R.string.btn_back_home, onClick = {
            navigateToHome()
        })
    }
}
