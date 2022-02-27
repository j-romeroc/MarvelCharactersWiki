package com.jarc.data.repositories

import com.jarc.core.utils.CustomError
import com.jarc.data.services.CharacterService
import com.jarc.domain.models.CharacterDetailModel
import com.jarc.domain.repositories.CharacterDetailRepo


class CharacterDetailRepoImpl(private val service: CharacterService) : CharacterDetailRepo {

    override suspend fun getCharacterDetail(
        characterId: String,
        callback: (Result<CharacterDetailModel>) -> Unit
    ) {

        service.getCharacterDetail(characterId) { result ->

            result.onSuccess { rawResponse ->
                callback(
                    Result.success(
                        rawResponse.mapToEntity().first().mapEntityToCharacterDetailModel()
                    )
                )
            }

            result.onFailure {
                it as CustomError
                callback(
                    Result.failure(
                        CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = it.getUnderlyingError()
                        )
                    )
                )
            }
        }
    }
}