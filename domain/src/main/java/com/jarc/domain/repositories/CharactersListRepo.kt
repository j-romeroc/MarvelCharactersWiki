package com.jarc.domain.repositories

import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.CharacterModel

interface CharactersListRepo {

    suspend fun fetchCharactersList(
        offsetFactor: Int,
        callback: (LayerResult<List<CharacterModel>>?) -> Unit
    )

    suspend fun getCharactersList(
        offsetFactor: Int,
        callback: (Result<List<CharacterModel>>) -> Unit)
}