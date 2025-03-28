package com.gcolina.rickandmorty.presentation.detail.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcolina.rickandmorty.core.navigation.NavManager
import com.gcolina.rickandmorty.data.repository.CharacterRepository
import com.gcolina.rickandmorty.presentation.home.model.Character
import com.gcolina.rickandmorty.utils.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val navManager: NavManager
) : ViewModel() {

    private var _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun getCharacterById() {
        val id = navManager.getCharacterId()
        if (id != null) {
            isLoading(true)
            viewModelScope.launch {
                characterRepository.getCharacterById(id).collect { result ->
                    result.onSuccess { character ->
                        _uiState.value = _uiState.value.copy(character = character.toDomain())
                    }.onFailure {
                        Log.e("LogGeneral", "fetchData: ${it.message}")
                        _uiState.value = _uiState.value.copy(error = "Character not found")
                    }
                    isLoading(false)
                }
            }
        } else {
            isLoading(false)
            _uiState.value = _uiState.value.copy(error = "Character not found")
        }
    }

    fun isLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }

}

data class DetailUiState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)