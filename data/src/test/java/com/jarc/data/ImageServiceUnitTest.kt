package com.jarc.data

import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.data.services.ImageService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class ImageServiceUnitTest {
    private lateinit var service : ImageService

    @Before
    fun setup(){
        service = ImageService()

    }

    @Test
    fun `should call for image and get a response in form of LayerResult`(){
        runBlocking {
            service.fetchImage("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"){ data ->
                assert(data is LayerResult)
            }
        }
    }

    @Test
    fun `should call for image without extension and get an httpException code 403`(){
        runBlocking {
            service.fetchImage("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"){ data ->
                val customError = (data as LayerResult.Error).error
                val httpException = (customError as CustomError).getUnderlyingError() as HttpException
                assert(httpException.code() == 403)
            }
        }
    }


    //Cannot perform this test because there no way to mock the bytearray -> bitmap converter inside the service
//    @Test
//    fun `should call for image and get raw ByteArray`(){
//        runBlocking {
//            service.fetchImage("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"){ data ->
//                val result = (data as LayerResult.Success)
//                assert(result.value is Bitmap)
//            }
//        }
//    }
}