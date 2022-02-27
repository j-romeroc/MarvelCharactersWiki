package com.jarc.data

import com.jarc.core.utils.CustomError
import com.jarc.data.entities.CharactersRawResponse
import com.jarc.data.repositories.CharacterDetailRepoImpl
import com.jarc.data.services.CharacterService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharacterDetailTest {

    private lateinit var service: CharacterService
    private lateinit var repo: CharacterDetailRepoImpl

    @Before
    fun setup() {
        service = mock()
        repo = CharacterDetailRepoImpl(service)
    }

    @Test
    fun `should call character service and get Result isFailure`() {

        runBlocking {

            whenever(service.getCharacterDetail(eq("someId"), any())).thenAnswer {
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

            repo.getCharacterDetail("someId") { result ->
                assert(result.isFailure)
            }
        }
    }
}