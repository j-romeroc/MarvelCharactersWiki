package com.jarc.domain.repositories

import com.jarc.core.utils.LayerResult
import com.jarc.domain.entities.CharacterEntity

interface CharactersListRepo {

    suspend fun fetchCharactersList(
        offsetFactor: Int,
        callback: (LayerResult<List<CharacterEntity>>?) -> Unit
    )
}