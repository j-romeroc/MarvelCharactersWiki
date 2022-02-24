package com.jarc.data.repositories


import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.data.entities.CharactersRawResponse
import com.jarc.data.services.CharacterService
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.repositories.CharactersListRepo

class CharactersListRepoImpl(private val service: CharacterService) : CharactersListRepo {

    override suspend fun fetchCharactersList(
        offsetFactor: Int,
        callback: (LayerResult<List<CharacterModel>>?) -> Unit
    ) {

        service.fetchCharactersList(offsetFactor) { result: LayerResult<CharactersRawResponse> ->

            try {
                when (result) {
                    is LayerResult.Success -> {

                        val characters = result.value?.mapToEntity()?.map { it.mapEntityToCharacterModel() }

                        callback(LayerResult.Success(characters))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError()
                        )
                    }
                }
            } catch (e: Throwable) {

                callback(LayerResult.Error(e))
            }
        }

    }

    override suspend fun getCharactersList(
        offsetFactor: Int,
        callback: (Result<List<CharacterModel>>) -> Unit
    ) {

        service.getCharactersList(offsetFactor) { result ->

            result.onSuccess { rawResponse ->
                callback(Result.success(rawResponse.mapToEntity().map { it.mapEntityToCharacterModel() }))
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