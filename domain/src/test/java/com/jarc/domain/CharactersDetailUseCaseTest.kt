package com.jarc.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.repositories.CharacterDetailRepo
import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersDetailUseCaseTest {

    private lateinit var useCase: CharacterDetailUseCase
    private lateinit var repo: CharacterDetailRepo


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repo = mock()
        useCase = CharacterDetailUseCase(repo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should call UseCase and get Result isSuccess`() {

        whenever(

            runBlocking { repo.getCharacterDetail(eq("someId"), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((Result<List<CharacterModel>>) -> Unit)>(1)
            callback(Result.success(mock()))
        }

        useCase.executeCall("someId") { result ->
            assert(result.isSuccess)
        }
    }


    @Test
    fun `should call UseCase and get Result isFailure`() {

        whenever(

            runBlocking { repo.getCharacterDetail(eq("someId"), any()) }

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

        useCase.executeCall("someId") { result ->
            assert(result.isFailure)
        }
    }
}