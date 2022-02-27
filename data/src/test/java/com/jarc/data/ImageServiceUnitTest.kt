package com.jarc.data

import com.jarc.core.utils.CustomError
import com.jarc.data.services.ImageService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class ImageServiceUnitTest {
    private lateinit var service: ImageService

    @Before
    fun setup() {
        service = ImageService()
    }

    @Test
    fun `should fetch image without extension and get a 403 httpException code`() {
        runBlocking {
            service.getImage("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available") { result ->

                result.onSuccess {
                    assert(false)
                }

                result.onFailure {
                    val httpException = (it as CustomError).getUnderlyingError() as HttpException
                    assert(httpException.code() == 403)
                }
            }
        }
    }

}