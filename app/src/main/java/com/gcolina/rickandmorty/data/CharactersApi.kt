package com.gcolina.rickandmorty.data

import com.gcolina.rickandmorty.data.models.response.CharacterDto
import com.gcolina.rickandmorty.data.models.response.CharacterResponse
import com.gcolina.rickandmorty.utils.Constants.Companion.CHARACTERS_ENDPOINT
import com.gcolina.rickandmorty.utils.Constants.Companion.DETAILS_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApi {
    @GET(CHARACTERS_ENDPOINT)
    suspend fun getCharacters(): Response<CharacterResponse>

    @GET(DETAILS_ENDPOINT)
    suspend fun getCharacterById(@Path("id") id: Int): Response<CharacterDto>
}
