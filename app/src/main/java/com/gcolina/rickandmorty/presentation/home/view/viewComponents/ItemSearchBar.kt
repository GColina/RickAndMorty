package com.gcolina.rickandmorty.presentation.home.view.viewComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.gcolina.rickandmorty.R
import com.gcolina.rickandmorty.presentation.home.model.Character
import com.gcolina.rickandmorty.utils.noRippleClickable

@Composable
fun ItemSearchBar(character: Character, onCharacterSelected: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = character.name,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.min_padding))
                .noRippleClickable { onCharacterSelected(character.id) },
            color = colorResource(R.color.tertiary_color)
        )
        Spacer(
            Modifier
                .height(dimensionResource(R.dimen.height_div_filter))
                .padding(horizontal = dimensionResource(R.dimen.big_padding))
                .background(colorResource(R.color.tertiary_color))
                .fillMaxWidth()
                .alpha(0.2f)
        )
    }
}