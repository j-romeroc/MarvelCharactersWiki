package com.jarc.domain.usecases

import com.jarc.domain.models.CharacterDetailModel
import com.jarc.domain.repositories.CharacterDetailRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharacterDetailUseCase(private val characterDetailRepo: CharacterDetailRepo) {

    fun executeCall(characterId: String, callback: (Result<CharacterDetailModel>) -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            characterDetailRepo.getCharacterDetail(characterId) { result ->

                callback(result)
            }
        }
    }

}