package com.jarc.data

import android.graphics.Bitmap
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.data.repositories.ImageRepoImpl
import com.jarc.data.services.ImageService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ImageRepoTest {

    private lateinit var service : ImageService
    private lateinit var repo: ImageRepoImpl
    private val URL = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"


    @Before
    fun setup(){

        service = mock()
        repo = ImageRepoImpl(service)

    }

    @Test
    fun `should succeed calling service and get LayerResult-Success`(){

        runBlocking {

            whenever(service.fetchImage(eq(URL), any())).thenAnswer {

                val callback = it.getArgument<((LayerResult<Bitmap>) -> Unit)>(1)
                callback.invoke(LayerResult.Success(mock()))
            }

            repo.fetchImage(URL){ result ->
                assert(result is LayerResult.Success)
            }
        }



    }

    @Test
    fun `should fail calling service and get LayerResult-Error`(){

        runBlocking {

            whenever(service.fetchImage(eq(URL), any())).thenAnswer {
                val callback = it.getArgument<((LayerResult<Bitmap>) -> Unit)>(1)
                callback.invoke(
                    LayerResult.Error(
                        CustomError(Throwable("TestException"),
                            CustomError.OriginLayer.DATA_LAYER)
                    ))
            }

            repo.fetchImage(URL){result ->
                assert(result is LayerResult.Error)
            }
        }
    }
}