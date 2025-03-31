package com.gcolina.rickandmorty.presentation.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcolina.rickandmorty.core.navigation.NavManager
import com.gcolina.rickandmorty.data.repository.CharacterRepository
import com.gcolina.rickandmorty.presentation.home.model.Character
import com.gcolina.rickandmorty.presentation.home.model.Info
import com.gcolina.rickandmorty.utils.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val navManager: NavManager
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun fetchData() {
        viewModelScope.launch {
            characterRepository.getCharacters().collect { result ->
                result.onSuccess { list ->
                    _uiState.value = _uiState.value.copy(
                        info = list.info.toDomain(),
                        characters = list.results.map { result -> result.toDomain() })
                }.onFailure {
                    _uiState.value = _uiState.value.copy(error = it.message)
                }
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun saveCharacterId(characterId: Int) {
        navManager.updateCharacterId(characterId)
    }

    fun onLoadMore() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingMore = true)
            characterRepository.getNextPage(uiState.value.info?.next ?: "").collect { result ->
                result.onSuccess { list ->
                    _uiState.value = _uiState.value.copy(
                        info = list.info.toDomain(),
                        characters = _uiState.value.characters + list.results.map { result -> result.toDomain() })
                }.onFailure {
                    _uiState.value = _uiState.value.copy(error = it.message)
                }
                _uiState.value = _uiState.value.copy(isLoadingMore = false)
            }
        }
    }

    fun onSearchByName() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingSearch = true)
            Log.e("LogGeneral", "onSearchByName: ${uiState.value.etSearchByName}")
            characterRepository.getCharacterByName(uiState.value.etSearchByName.toString())
                .collect { result ->
                    result.onSuccess { list ->
                        _uiState.value = _uiState.value.copy(
                            info = list.info.toDomain(),
                            charactersFiltered = list.results.map { result -> result.toDomain() })
                    }.onFailure {
                        Log.e("LogGeneral", "onSearchByName: ${it.message}")
                    }
                    _uiState.value = _uiState.value.copy(isLoadingSearch = false)
                }
        }
    }

    fun onTextChange(string: String) {
        _uiState.value = _uiState.value.copy(etSearchByName = string)
    }

}

data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val characters: List<Character> = emptyList(),
    val charactersFiltered: List<Character> = emptyList(),
    val info: Info? = null,
    val isLoadingMore: Boolean = false,
    val etSearchByName: String? = null,
    val isLoadingSearch: Boolean = false,
)