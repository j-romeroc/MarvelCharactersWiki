package com.jarc.domain.repositories

import com.jarc.domain.models.CharacterModel

interface CharactersListRepo {

    suspend fun getCharactersList(
        offsetFactor: Int,
        callback: (Result<List<CharacterModel>>) -> Unit
    )
}