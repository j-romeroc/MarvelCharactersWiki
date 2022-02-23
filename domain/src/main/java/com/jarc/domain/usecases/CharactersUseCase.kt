package com.jarc.domain.usecases

import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.entities.*
import com.jarc.domain.repositories.CharactersListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CharactersUseCase(private val characterRepo: CharactersListRepo) {

    private var offset = 1

    fun execute(callback: (LayerResult<List<CharacterEntity>>?) -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            characterRepo.fetchCharactersList(offset) { result ->

                try {

                    when (result) {

                        is LayerResult.Success -> {

                            callback(LayerResult.Success(result.value))
                            offset += 1
                        }
                        is LayerResult.Error -> {

                            throw CustomError(
                                originLayer = (result.error as CustomError).getErrorOriginLayer(),
                                underLyingError = (result.error as CustomError).getUnderlyingError()
                            )
                        }
                        else -> {}
                    }
                } catch (ce: CustomError) {

                    callback(LayerResult.Error(ce))
                } catch (e: Throwable) {

                    callback(
                        LayerResult.Error(
                            CustomError(
                                originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                                underLyingError = e
                            )
                        )
                    )
                }
            }
        }
    }
}