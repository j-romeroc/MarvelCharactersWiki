package com.jarc.domain.repositories

import com.jarc.domain.models.CharacterDetailModel

interface CharacterDetailRepo {

    suspend fun getCharacterDetail(
        characterId: String,
        callback: (Result<CharacterDetailModel>) -> Unit
    )
}