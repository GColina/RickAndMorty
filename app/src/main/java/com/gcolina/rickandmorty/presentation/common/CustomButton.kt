package com.gcolina.rickandmorty.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.gcolina.rickandmorty.R

@Composable
fun CustomButton(titleButton: Int, onClick: () -> Unit) {

    Button(modifier = Modifier.fillMaxWidth(), onClick = { onClick() }, content = {
        Text(
            text = stringResource(titleButton),
            modifier = Modifier.padding(dimensionResource(R.dimen.mid_padding)),
        )
    })
}
