package com.gcolina.rickandmorty.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NavManager @Inject constructor() {

    private var _characterId = MutableStateFlow<Int?>(null)
    val characterId: StateFlow<Int?> = _characterId

    fun updateCharacterId(characterId: Int) {
        _characterId.value = characterId
    }

    fun clearCharacterId() {
        _characterId.value = null
    }

    fun getCharacterId(): Int? {
        return _characterId.value
    }
}