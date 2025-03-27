package com.gcolina.rickandmorty.utils

import com.gcolina.rickandmorty.data.models.response.CharacterDto
import com.gcolina.rickandmorty.presentation.home.model.Character

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
        episodeCount = episode.size
    )
}