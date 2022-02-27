package com.jarc.marvelcharacterswiki.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jarc.core.utils.AspectRatio
import com.jarc.domain.models.CharacterDetailModel
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.models.Thumbnail
import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.usecases.CharactersUseCase
import com.jarc.domain.usecases.ImagesUseCase
import com.jarc.marvelcharacterswiki.ui.models.CharacterImage
import com.jarc.marvelcharacterswiki.R

class CharacterViewModel(
    private val characterUseCase: CharactersUseCase,
    private val characterDetailUseCase: CharacterDetailUseCase,
    private val imagesUseCase: ImagesUseCase
) : ViewModel() {

    val characterListLiveData: MutableLiveData<List<CharacterModel>> = MutableLiveData()
    val characterDetailLiveData: MutableLiveData<CharacterDetailModel> = MutableLiveData()
    val detailImageLiveData: MutableLiveData<Bitmap> = MutableLiveData()
    val getListError: MutableLiveData<Throwable> = MutableLiveData()
    val getDetailError: MutableLiveData<Throwable> = MutableLiveData()
    val getImageError: MutableLiveData<Throwable> = MutableLiveData()
    val listImageLiveData: MutableLiveData<CharacterImage> = MutableLiveData()

    fun getCharacterList() {
        characterUseCase.executeCall { listResult ->

            listResult.onSuccess { characterList ->

                characterListLiveData.postValue(characterList)
            }

            listResult.onFailure { error ->

                getListError.postValue(error)
            }
        }
    }

    fun getCharacterDetail(characterId: String) {
        characterDetailUseCase.executeCall(characterId) { result ->

            result.onSuccess {

                characterDetailLiveData.postValue(it)
            }

            result.onFailure { error ->

                getDetailError.postValue(error)
            }

        }
    }

    fun getImageDetail(
        imageInfo: Thumbnail,
        origin: AspectRatio.Origin
    ) {
        imagesUseCase.executeCall(
            Thumbnail(
                imageInfo.path,
                imageInfo.extension
            ), origin
        ) { result ->
            result.onSuccess { detailImageLiveData.postValue(it) }
            result.onFailure { getImageError.postValue(it) }
        }
    }

    fun getImageForList(
        imageInfo: Thumbnail,
        position: Int
    ) {
        imagesUseCase.executeCall(
            Thumbnail(
                imageInfo.path,
                imageInfo.extension
            ), AspectRatio.Origin.LIST
        ) { result ->

            result.onSuccess {
                listImageLiveData.postValue(
                    CharacterImage(
                        image = it,
                        position = position
                    )
                )
            }

            result.onFailure {
                listImageLiveData.postValue(
                    CharacterImage(
                        image = null,
                        position = position
                    )
                )
            }
        }
    }

}