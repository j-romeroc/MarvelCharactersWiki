package com.jarc.data

import com.jarc.core.utils.CustomError
import com.jarc.data.services.CharacterService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class CharactersServiceUnitTest {


    private lateinit var service: CharacterService

    @Before
    fun setup() {
        service = CharacterService()

    }


    @Test
    fun `should fetch character detail with incorrect id and get a 404 httpException code`() {
        runBlocking {
            service.getCharacterDetail("2") { result ->

                result.onSuccess {
                    assert(false)
                }

                result.onFailure {
                    val httpException = (it as CustomError).getUnderlyingError() as HttpException
                    assert(httpException.code() == 404)
                }
            }
        }
    }


    @Test
    fun `should fetch character detail with a valid id and get Result isSuccess`() {
        runBlocking {
            service.getCharacterDetail("1017100") { result ->

                assert(result.isSuccess)

            }
        }
    }


}