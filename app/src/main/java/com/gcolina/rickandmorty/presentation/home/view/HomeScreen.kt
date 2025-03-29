package com.gcolina.rickandmorty.presentation.home.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gcolina.rickandmorty.R
import com.gcolina.rickandmorty.presentation.common.Loader
import com.gcolina.rickandmorty.presentation.home.model.Character
import com.gcolina.rickandmorty.presentation.home.view.viewComponents.ItemCharacter
import com.gcolina.rickandmorty.presentation.home.viewModel.HomeViewModel
import com.gcolina.rickandmorty.utils.noRippleClickable

@Composable
fun HomeScreen(
    navigateToDetail: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToError: () -> Unit
) {

    val uiState by homeViewModel.uiState.collectAsState()
    var isSearchActive by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchData()
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { lastVisibleIndex ->
            if (lastVisibleIndex != null && lastVisibleIndex >= uiState.characters.size - 2) {
                homeViewModel.onLoadMore()
            }
        }
    }

    Loader(
        isLoading = uiState.isLoading,
        isApiError = uiState.error != null,
        navigateToError = { navigateToError() }) {

        Column(
            modifier = Modifier
                .background(colorResource(R.color.primary_color))
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.big_padding))
                .noRippleClickable {
                    isSearchActive = false
                    focusManager.clearFocus(true)
                }) {
            Image(
                painter = painterResource(R.drawable.ic_logo_rickandmorty),
                contentDescription = null,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.mid_padding))
                    .height(50.dp)
                    .fillMaxWidth(),
                alignment = Alignment.TopCenter
            )

            HomeSearchBar(
                query = uiState.etSearchByName ?: "",
                onQueryChange = { homeViewModel.onTextChange(it) },
                onSearch = {
                    homeViewModel.onSearchByName()
                    isSearchActive = false
                },
                active = isSearchActive,
                characters = uiState.characters,
                onActiveChange = { isSearchActive = it })


            Text(
                text = stringResource(R.string.title),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                verticalArrangement = Arrangement.Top
            ) {
                items(uiState.characters.size) { position ->
                    ItemCharacter(
                        character = uiState.characters[position],
                        onCharacterSelected = { characterId ->
                            homeViewModel.saveCharacterId(characterId)
                            navigateToDetail()
                        })
                }
                item {
                    AnimatedVisibility(uiState.isLoadingMore) {
                        Spacer(Modifier.height(dimensionResource(R.dimen.mid_padding)))
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = colorResource(R.color.tertiary_color))
                            Spacer(Modifier.height(dimensionResource(R.dimen.big_padding)))
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    characters: List<Character>
) {
    SearchBar(
        query = query,
        shape = RoundedCornerShape(corner = CornerSize(dimensionResource(R.dimen.min_padding))),
        onQueryChange = { newQuery -> onQueryChange(newQuery) },
        onSearch = { onSearch() },
        active = active,
        onActiveChange = onActiveChange,
        placeholder = { Text(text = stringResource(R.string.search_by_name), color = Color.White) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = colorResource(R.color.tertiary_color),
                modifier = Modifier.noRippleClickable {
                    onSearch()
                }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.mid_padding))
            .heightIn(max = 300.dp),
        colors = SearchBarDefaults.colors(
            containerColor = colorResource(R.color.secondary_color),
            dividerColor = Color.Transparent,
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                cursorColor = colorResource(R.color.tertiary_color),
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
                focusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                focusedTextColor = Color.White
            )
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
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

                Text(
                    text = characters.get(position).name,
                    modifier = Modifier.padding(dimensionResource(R.dimen.mid_padding)),
                    color = colorResource(R.color.tertiary_color)
                )
                Spacer(
                    Modifier
                        .height(dimensionResource(R.dimen.height_div_filter))
                        .background(colorResource(R.color.tertiary_color))
                        .width(dimensionResource(R.dimen.big_padding))
                        .align(Alignment.End)
                )
            }
        }
    }
}
