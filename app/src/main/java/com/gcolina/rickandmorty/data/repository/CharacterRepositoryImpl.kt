package com.gcolina.rickandmorty.data.repository

import com.gcolina.rickandmorty.data.models.response.CharacterDto
import com.gcolina.rickandmorty.data.models.response.CharacterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterDataSource: CharacterDataSource) :
    CharacterRepository {
    override suspend fun getCharacters(): Flow<Result<CharacterResponse>> = flow {
        emit(characterDataSource.getCharacters())
    }
    override suspend fun getCharacterById(id: Int): Flow<Result<CharacterDto>> = flow {
        emit(characterDataSource.getCharacterById(id))
    }
    override suspend fun getNextPage(url: String): Flow<Result<CharacterResponse>> = flow {
        emit(characterDataSource.getNextPage(url))
    }

    override suspend fun getCharacterByName(name: String): Flow<Result<CharacterResponse>> = flow {
        emit(characterDataSource.getCharacterByName(name))
    }
}
