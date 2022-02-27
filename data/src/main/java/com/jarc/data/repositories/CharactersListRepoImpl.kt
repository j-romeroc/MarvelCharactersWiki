package com.jarc.data.repositories


import com.jarc.core.utils.CustomError
import com.jarc.data.services.CharacterService
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.repositories.CharactersListRepo

class CharactersListRepoImpl(private val service: CharacterService) : CharactersListRepo {

    override suspend fun getCharactersList(
        offsetFactor: Int,
        callback: (Result<List<CharacterModel>>) -> Unit
    ) {

        service.getCharactersList(offsetFactor) { result ->

            result.onSuccess { rawResponse ->
                callback(
                    Result.success(
                        rawResponse.mapToEntity().map { it.mapEntityToCharacterModel() })
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