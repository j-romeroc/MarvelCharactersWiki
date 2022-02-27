package com.jarc.domain

import com.nhaarman.mockitokotlin2.*
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.repositories.CharactersListRepo
import com.jarc.domain.usecases.CharactersUseCase
import com.jarc.domain.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CharactersUseCaseTest {

    private lateinit var useCase: CharactersUseCase
    private lateinit var repo: CharactersListRepo


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repo = mock()
        useCase = CharactersUseCase(repo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should call UseCase and get Result isSuccess`() {

        whenever(

            runBlocking { repo.getCharactersList(eq(1), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((Result<List<CharacterModel>>) -> Unit)>(1)
            callback(Result.success(mock()))
        }

        useCase.executeCall { result ->
            assert(result.isSuccess)
        }
    }


    @Test
    fun `should call UseCase and get Result isFailure`() {

        whenever(

            runBlocking { repo.getCharactersList(eq(1), any()) }

        ).thenAnswer {
            val callback = it.getArgument<((Result<List<CharacterModel>>) -> Unit)>(1)
            callback(
                (Result.failure(
                    CustomError(
                        originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                        underLyingError = Throwable("TestException")
                    )
                ))
            )
        }

        useCase.executeCall { result ->
            assert(result.isFailure)
        }
    }

}