package com.jarc.domain.usecases

import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.*
import com.jarc.domain.repositories.CharacterDetailRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharacterDetailUseCase(private val characterDetailRepo: CharacterDetailRepo) {

    fun execute(characterId: String, callback: (LayerResult<CharacterModel>?) -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            characterDetailRepo.fetchCharacterDetail(characterId) { result ->

                try {
                    when (result) {
                        is LayerResult.Success -> {

                            callback(LayerResult.Success(result.value))
                        }
                        is LayerResult.Error -> {

                            throw CustomError(
                                originLayer = (result.error as CustomError).getErrorOriginLayer(),
                                underLyingError = (result.error as CustomError).getUnderlyingError()
                            )
                        }
                        else -> {}
                    }
                } catch (e: Throwable) {

                    callback(
                        LayerResult.Error(
                            CustomError(
                                originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                                underLyingError = e
                            )
                        )
                    )

                } catch (ce: CustomError) {

                    callback(LayerResult.Error(ce))
                }

            }
        }
    }

    fun executeCall(characterId: String, callback: (Result<CharacterDetailModel>) -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            characterDetailRepo.getCharacterDetail(characterId) { result ->

                callback(result)
            }
        }
    }

}