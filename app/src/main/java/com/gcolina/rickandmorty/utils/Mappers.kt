package com.gcolina.rickandmorty.utils

import com.gcolina.rickandmorty.data.models.response.CharacterDto
import com.gcolina.rickandmorty.data.models.response.InfoResponse
import com.gcolina.rickandmorty.presentation.home.model.Character
import com.gcolina.rickandmorty.presentation.home.model.Info

fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        originName = origin.name,
        locationName = location.name,
        image = image,
        episodeCount = episode.size,
        episodeList = episode
    )
}

fun InfoResponse.toDomain(): Info {
    return Info(
        count = count, pages = pages, next = next, prev = prev
    )
}

