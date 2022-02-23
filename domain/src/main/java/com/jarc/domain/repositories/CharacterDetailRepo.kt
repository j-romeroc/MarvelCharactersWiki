package com.jarc.domain.repositories

import com.jarc.core.utils.LayerResult
import com.jarc.domain.entities.CharacterEntity

interface CharacterDetailRepo {

    suspend fun fetchCharacterDetail(
        characterId: String,
        callback: (LayerResult<CharacterEntity>?) -> Unit
    )
}