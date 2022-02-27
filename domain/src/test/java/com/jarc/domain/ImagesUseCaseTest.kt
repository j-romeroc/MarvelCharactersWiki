package com.jarc.domain

import android.graphics.Bitmap
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.Thumbnail
import com.jarc.domain.repositories.ImageRepo
import com.jarc.domain.usecases.ImagesUseCase
import com.jarc.domain.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImagesUseCaseTest {

    private lateinit var useCase: ImagesUseCase
    private lateinit var repo: ImageRepo


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repo = mock()
        useCase = ImagesUseCase(repo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should call UseCase and get Result isSuccess`() {

        whenever(

            runBlocking { repo.getImage(eq("somePath.someExtension"), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((Result<Bitmap>) -> Unit)>(1)
            callback(Result.success(mock()))
        }

        val thumbnail = Thumbnail(path = "somePath", extension = "someExtension")
        useCase.executeCall(thumbnail, mock()) { result ->
            assert(result.isSuccess)
        }
    }


    @Test
    fun `should call UseCase and get Result isFailure`() {

        whenever(

            runBlocking { repo.getImage(eq("somePath.someExtension"), any()) }

        ).thenAnswer {
            val callback = it.getArgument<((Result<Bitmap>) -> Unit)>(1)
            callback(
                (Result.failure(
                    CustomError(
                        originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                        underLyingError = Throwable("TestException")
                    )
                ))
            )
        }

        val thumbnail = Thumbnail(path = "somePath", extension = "someExtension")
        useCase.executeCall(thumbnail, mock()) { result ->
            assert(result.isFailure)
        }
    }
}