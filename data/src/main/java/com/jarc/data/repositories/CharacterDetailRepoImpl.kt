package com.jarc.data.repositories


import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.data.services.CharacterService
import com.jarc.domain.repositories.CharacterDetailRepo
import com.jarc.domain.entities.CharacterEntity


class CharacterDetailRepoImpl(private val service: CharacterService): CharacterDetailRepo {


    override suspend fun fetchCharacterDetail(characterId: String,
                                      callback: (LayerResult<CharacterEntity>?) -> Unit) {


        service.fetchCharacterDetail(characterId) { result ->

            try{
                when (result) {
                    is LayerResult.Success -> {

                        val character = result.value?.mapToData()?.firstOrNull()?.mapDataToEntity()

                        callback(LayerResult.Success(character))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError())
                    }
                }
            }catch (e: Throwable){

                callback(LayerResult.Error(e))
            }
        }
    }
}