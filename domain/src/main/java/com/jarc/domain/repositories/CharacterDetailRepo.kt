package com.jarc.domain.repositories

import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.CharacterDetailModel
import com.jarc.domain.models.CharacterModel

interface CharacterDetailRepo {

    suspend fun fetchCharacterDetail(
        characterId: String,
        callback: (LayerResult<CharacterModel>?) -> Unit
    )

    suspend fun getCharacterDetail(characterId: String,
                                   callback: (Result<CharacterDetailModel>) -> Unit)
}