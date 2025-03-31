package com.gcolina.rickandmorty.presentation.home.view.viewComponents

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gcolina.rickandmorty.R
import com.gcolina.rickandmorty.presentation.home.model.Character
import com.gcolina.rickandmorty.utils.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearch(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    characters: List<Character>,
    onCharacterSelected: (Int) -> Unit
) {
    val actualExpanded = active && characters.isNotEmpty()
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query, onQueryChange = onQueryChange, onSearch = {
                    Log.d("LogGeneral", "onSearch: $query")
                    onSearch()
                }, expanded = actualExpanded, onExpandedChange = onActiveChange, placeholder = {
                    Text(
                        text = stringResource(R.string.search_by_name),
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }, trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(R.color.tertiary_color),
                        modifier = Modifier.noRippleClickable { onSearch() })
                }, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.colors(
                    cursorColor = colorResource(R.color.tertiary_color),
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                    focusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                    unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = colorResource(R.color.tertiary_color),
                    unfocusedContainerColor = colorResource(R.color.tertiary_color),
                )
            )
        },
        expanded = actualExpanded,
        onExpandedChange = onActiveChange,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp)
            .padding(bottom = dimensionResource(R.dimen.mid_padding))
            .clip(RoundedCornerShape(corner = CornerSize(dimensionResource(R.dimen.min_padding)))),
        shape = RoundedCornerShape(corner = CornerSize(dimensionResource(R.dimen.min_padding))),
        colors = SearchBarDefaults.colors(
            containerColor = colorResource(R.color.secondary_color),
            dividerColor = Color.Transparent
        ),
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets
    ) {
        AnimatedVisibility(characters.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Text(
                        "Search Results: ${characters.size}",
                        modifier = Modifier.padding(dimensionResource(R.dimen.mid_padding)),
                        color = colorResource(R.color.tertiary_color)
                    )
                    Spacer(
                        Modifier
                            .height(dimensionResource(R.dimen.height_div_filter))
                            .fillMaxWidth()
                            .background(colorResource(R.color.tertiary_color))
                    )
                }
                items(characters.size) { position ->
                    ItemSearchBar(
                        character = characters[position], onCharacterSelected = {
                            onCharacterSelected(it)
                        })
                }
            }
        }
    }
}
