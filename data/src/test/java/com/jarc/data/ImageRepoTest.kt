package com.jarc.data

import android.graphics.Bitmap
import com.jarc.core.utils.CustomError
import com.jarc.data.repositories.ImageRepoImpl
import com.jarc.data.services.ImageService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ImageRepoTest {

    private lateinit var service: ImageService
    private lateinit var repo: ImageRepoImpl
    private val URL = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"


    @Before
    fun setup() {
        service = mock()
        repo = ImageRepoImpl(service)
    }

    @Test
    fun `should call image service and get Result isSuccess`() {

        runBlocking {

            whenever(service.getImage(eq(URL), any())).thenAnswer {

                val callback = it.getArgument<((Result<Bitmap>) -> Unit)>(1)
                callback.invoke(Result.success(mock()))
            }

            repo.getImage(URL) { result ->
                assert(result.isSuccess)
            }
        }

    }

    @Test
    fun `should call image service and get Result isFailure`() {

        runBlocking {

            whenever(service.getImage(eq(URL), any())).thenAnswer {
                val callback = it.getArgument<((Result<Bitmap>) -> Unit)>(1)
                callback.invoke(

                    (Result.failure(
                        CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = Throwable("TestException")
                        )
                    ))
                )
            }

            repo.getImage(URL) { result ->

                assert(result.isFailure)
            }
        }
    }
}