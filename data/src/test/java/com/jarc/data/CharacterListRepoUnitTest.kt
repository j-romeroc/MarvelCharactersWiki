package com.jarc.data

import com.jarc.core.utils.CustomError
import com.jarc.data.entities.CharactersRawResponse
import com.jarc.data.repositories.CharactersListRepoImpl
import com.jarc.data.services.CharacterService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharacterListRepoUnitTest {


    private lateinit var service: CharacterService
    private lateinit var repo: CharactersListRepoImpl

    @Before
    fun setup() {
        service = mock()
        repo = CharactersListRepoImpl(service)
    }

    @Test
    fun `should call character service and get Result isSuccess`() {

        runBlocking {

            whenever(service.getCharactersList(eq(1), any())).thenAnswer {
                val callback = it.getArgument<((Result<CharactersRawResponse>) -> Unit)>(1)
                callback.invoke(Result.success(mock()))
            }

            repo.getCharactersList(1) { result ->
                assert(result.isSuccess)
            }
        }
    }


    @Test
    fun `should call character service and get Result isFailure`() {

        runBlocking {

            whenever(service.getCharactersList(eq(1), any())).thenAnswer {
                val callback = it.getArgument<((Result<CharactersRawResponse>) -> Unit)>(1)
                callback.invoke(
                    (Result.failure(
                        CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = Throwable("TestException")
                        )
                    ))
                )
            }

            repo.getCharactersList(1) { result ->
                assert(result.isFailure)
            }
        }
    }
}