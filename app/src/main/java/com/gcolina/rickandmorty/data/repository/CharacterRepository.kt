package com.gcolina.rickandmorty.data.repository

import com.gcolina.rickandmorty.data.models.response.CharacterDto
import com.gcolina.rickandmorty.data.models.response.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(): Flow<Result<CharacterResponse>>

    suspend fun getCharacterById(id: Int): Flow<Result<CharacterDto>>
}
