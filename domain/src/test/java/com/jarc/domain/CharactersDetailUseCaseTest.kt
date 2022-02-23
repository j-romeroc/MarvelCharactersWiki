package com.jarc.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.entities.CharacterEntity
import com.jarc.domain.repositories.CharacterDetailRepo
import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersDetailUseCaseTest {

    private lateinit var useCase : CharacterDetailUseCase
    private lateinit var repo: CharacterDetailRepo


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){

        repo = mock()
        useCase = CharacterDetailUseCase(repo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should succeed calling Usecase and get LayerResult-Success`() {

        whenever(

                runBlocking { repo.fetchCharacterDetail(eq("someId"), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<List<CharacterEntity>>) -> Unit)>(1)
            callback(LayerResult.Success(mock()))
        }

        useCase.execute("someId") { result ->
            assert(result is LayerResult.Success)
        }
    }


    @Test
    fun `should fail calling usecase and get LayerResult-Error`(){

        whenever(

                runBlocking { repo.fetchCharacterDetail(eq("someId"), any()) }

        ).thenAnswer {
            val callback = it.getArgument<((LayerResult<List<CharacterEntity>>) -> Unit)>(1)
            callback(
                    LayerResult.Error(
                            CustomError(Throwable("TestException"),
                                    CustomError.OriginLayer.DATA_LAYER)
                    ))
        }

        useCase.execute("someId") { result ->
            assert(result is LayerResult.Error)
        }
    }
}