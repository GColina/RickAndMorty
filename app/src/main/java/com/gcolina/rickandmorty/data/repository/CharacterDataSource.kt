package com.gcolina.rickandmorty.data.repository

import android.util.Log
import com.gcolina.rickandmorty.data.CharactersApi
import com.gcolina.rickandmorty.data.models.response.CharacterDto
import com.gcolina.rickandmorty.data.models.response.CharacterResponse
import com.gcolina.rickandmorty.utils.BaseDataSource
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val charactersApi: CharactersApi
) : BaseDataSource() {
    suspend fun getCharacters(): Result<CharacterResponse> {
        return processResponse {
            charactersApi.getCharacters()
        }
    }

    suspend fun getCharacterById(id: Int): Result<CharacterDto> {
        return processResponse {
            charactersApi.getCharacterById(id)
        }
    }

    suspend fun getNextPage(url: String): Result<CharacterResponse> {
        return processResponse {
            charactersApi.getNextPage(url)
        }
    }

    suspend fun getCharacterByName(name: String): Result<CharacterResponse> {
        return processResponse {
            Log.e("LogGeneral", "getCharacterByName: $name")
            charactersApi.getCharacterByName(name)
        }
    }

}
