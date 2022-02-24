package com.jarc.data.repositories


import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.data.services.CharacterService
import com.jarc.domain.models.CharacterDetailModel
import com.jarc.domain.repositories.CharacterDetailRepo
import com.jarc.domain.models.CharacterModel


class CharacterDetailRepoImpl(private val service: CharacterService) : CharacterDetailRepo {


    override suspend fun fetchCharacterDetail(
        characterId: String,
        callback: (LayerResult<CharacterModel>?) -> Unit
    ) {

        service.fetchCharacterDetail(characterId) { result ->

            try {
                when (result) {
                    is LayerResult.Success -> {

                        val character =
                            result.value?.mapToEntity()?.firstOrNull()?.mapEntityToCharacterModel()

                        callback(LayerResult.Success(character))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError()
                        )
                    }
                    else -> {}
                }
            } catch (e: Throwable) {

                callback(LayerResult.Error(e))
            }
        }
    }


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