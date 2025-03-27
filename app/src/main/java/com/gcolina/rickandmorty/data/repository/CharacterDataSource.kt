package com.gcolina.rickandmorty.data.repository

import com.gcolina.rickandmorty.data.CharactersApi
import com.gcolina.rickandmorty.data.models.response.CharacterDto
import com.gcolina.rickandmorty.data.models.response.CharacterResponse
import com.gcolina.rickandmorty.utils.BaseDataSource
import javax.inject.Inject

class CharacterDataSource @Inject constructor(private val charactersApi: CharactersApi): BaseDataSource() {
    suspend fun getCharacters():Result<CharacterResponse> {
        return processResponse {
            charactersApi.getCharacters()
        }
    }

    suspend fun getCharacterById(id: Int): Result<CharacterDto> {
        return processResponse {
            charactersApi.getCharacterById(id)
        }
    }

}
