package com.jarc.domain.usecases

import com.jarc.domain.models.CharacterModel
import com.jarc.domain.repositories.CharactersListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CharactersUseCase(private val characterRepo: CharactersListRepo) {

    private var offset = 1

    fun executeCall(callback: (Result<List<CharacterModel>>) -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            characterRepo.getCharactersList(offset) { result ->
                callback(result)
            }
        }
    }

}