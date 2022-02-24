package com.jarc.marvelcharacterswiki

import android.graphics.Bitmap
import com.nhaarman.mockitokotlin2.*
import com.jarc.core.utils.AspectRatio
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.models.Publishings
import com.jarc.domain.models.Thumbnail
import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.usecases.CharactersUseCase
import com.jarc.domain.usecases.ImagesUseCase
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenter
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenterImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock


class CharacterPresenterTest {

    private lateinit var presenter: CharacterPresenter

    private val characterUseCase : CharactersUseCase = mock(CharactersUseCase::class.java)
    private val characterDetailUseCase: CharacterDetailUseCase = mock(CharacterDetailUseCase::class.java)
    private val imagesUseCase: ImagesUseCase = mock(ImagesUseCase::class.java)



    @Before
    fun setup(){

        presenter = CharacterPresenterImpl(
                characterUseCase,
                characterDetailUseCase,
                imagesUseCase)

    }

    @Test
    fun `should call for character list and get success response`(){

        whenever(

            runBlocking { characterUseCase.execute(any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<List<CharacterModel>>?) -> Unit)>(0)
            callback(LayerResult.Success(getCharacterEntityMockList()))
        }

        presenter.fetchCharacterList { result ->
            assert(result is LayerResult.Success)
        }
    }


    @Test
    fun `should call for character list and get error response with Presentation Layer origin`(){

        whenever(

            runBlocking { characterUseCase.execute(any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<List<CharacterModel>>?) -> Unit)>(0)
            callback(LayerResult.Error(
                CustomError(Throwable("TestException"),
                    CustomError.OriginLayer.PRESENTATION_LAYER)
            ))
        }

        presenter.fetchCharacterList { result ->
            val error = result as LayerResult.Error
            val originLayer = (error.error as CustomError).getErrorOriginLayer()

            assert(originLayer == CustomError.OriginLayer.PRESENTATION_LAYER)
        }
    }

    @Test
    fun `should call for character detail and get success`(){

        whenever(

            runBlocking { characterDetailUseCase.execute(eq("someId"),any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<CharacterModel>?) -> Unit)>(1)
            callback(LayerResult.Success(mock()))
        }

        presenter.fetchCharacterDetail("someId") { result ->
            assert(result is LayerResult.Success)
        }
    }

    @Test
    fun `should call for character detail and get error response with Presentation Layer origin`(){

        whenever(

            runBlocking { characterDetailUseCase.execute(eq("someId"),any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<List<CharacterModel>>?) -> Unit)>(1)
            callback(LayerResult.Error(
                CustomError(Throwable("TestException"),
                    CustomError.OriginLayer.PRESENTATION_LAYER)
            ))
        }

        presenter.fetchCharacterDetail("someId") { result ->
            val error = result as LayerResult.Error
            val originLayer = (error.error as CustomError).getErrorOriginLayer()

            assert(originLayer == CustomError.OriginLayer.PRESENTATION_LAYER)
        }
    }

    @Test
    fun `should call for image and get success`(){

        whenever(

            runBlocking { imagesUseCase.execute(eq(Thumbnail("","")),eq(AspectRatio.Origin.LIST),any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<Bitmap>) -> Unit)>(2)
            callback(LayerResult.Success(mock()))
        }

        presenter.fetchImage(com.jarc.marvelcharacterswiki.models.Thumbnail("",""),AspectRatio.Origin.LIST) { result ->
            assert(result is LayerResult.Success)
        }
    }

    @Test
    fun `should call for image and get error response with Presentation Layer origin`(){

        whenever(

            runBlocking { imagesUseCase.execute(eq(Thumbnail("","")),eq(AspectRatio.Origin.LIST),any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<Bitmap>) -> Unit)>(2)
            callback(LayerResult.Error(
                CustomError(Throwable("TestException"),
                    CustomError.OriginLayer.PRESENTATION_LAYER)
            ))
        }

        presenter.fetchImage(com.jarc.marvelcharacterswiki.models.Thumbnail("",""),AspectRatio.Origin.LIST) { result ->

            val error = result as LayerResult.Error
            val originLayer = (error.error as CustomError).getErrorOriginLayer()

            assert(originLayer == CustomError.OriginLayer.PRESENTATION_LAYER)
        }
    }



    private fun getCharacterEntityMockList(): List<CharacterModel>{
        val list: MutableList<CharacterModel> = mutableListOf()

        for(i in 0..5){

            list.add(
                CharacterModel(
                    id = i.toLong(),
                    name = "",
                    description = "",
                    modified = "",
                    thumbnail = Thumbnail(path = "",extension = ""),
                    resourceURI = "",
                    comics = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0),
                    series = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0),
                    stories = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0),
                    events = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0),
                    urls = listOf())
            )
        }

        return list
    }

}