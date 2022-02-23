package com.jarc.domain

import android.graphics.Bitmap
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.entities.Thumbnail
import com.jarc.domain.repositories.ImageRepo
import com.jarc.domain.usecases.ImagesUseCase
import com.jarc.domain.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImagesUseCaseTest {

    private lateinit var useCase : ImagesUseCase
    private lateinit var repo: ImageRepo


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){

        repo = mock()
        useCase = ImagesUseCase(repo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should succeed calling Usecase and get LayerResult-Success`() {

        whenever(

                runBlocking { repo.fetchImage(eq("somePath.someExtension"), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<Bitmap>) -> Unit)>(1)
            callback(LayerResult.Success(mock()))
        }

        val thumbnail = Thumbnail(path = "somePath",extension = "someExtension")
        useCase.execute(thumbnail, mock()) { result ->
            assert(result is LayerResult.Success)
        }
    }


    @Test
    fun `should fail calling usecase and get LayerResult-Error`(){

        whenever(

                runBlocking { repo.fetchImage(eq("somePath.someExtension"), any()) }

        ).thenAnswer {
            val callback = it.getArgument<((LayerResult<Bitmap>) -> Unit)>(1)
            callback(
                    LayerResult.Error(
                            CustomError(Throwable("TestException"),
                                    CustomError.OriginLayer.DATA_LAYER)
                    ))
        }

        val thumbnail = Thumbnail(path = "somePath",extension = "someExtension")
        useCase.execute(thumbnail, mock()) { result ->
            assert(result is LayerResult.Error)
        }
    }
}