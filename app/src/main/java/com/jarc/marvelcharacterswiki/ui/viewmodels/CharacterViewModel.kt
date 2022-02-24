package com.jarc.marvelcharacterswiki.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jarc.core.utils.AspectRatio
import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.CharacterDetailModel
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.models.Thumbnail
import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.usecases.CharactersUseCase
import com.jarc.domain.usecases.ImagesUseCase

class CharacterViewModel(
    private val characterUseCase: CharactersUseCase,
    private val characterDetailUseCase: CharacterDetailUseCase,
    private val imagesUseCase: ImagesUseCase
) : ViewModel() {

    val characterListLiveData: MutableLiveData<List<CharacterModel>> = MutableLiveData()
    val characterDetailLiveData: MutableLiveData<CharacterDetailModel> = MutableLiveData()
    val imageBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    val fetchListError: MutableLiveData<Throwable> = MutableLiveData()
    val fetchDetailError: MutableLiveData<Throwable> = MutableLiveData()
    val fetchImageError: MutableLiveData<Throwable> = MutableLiveData()

    fun fetchCharacterList() {
        characterUseCase.executeCall { listResult ->

            listResult.onSuccess { characterList ->

                characterListLiveData.postValue(characterList)
            }

            listResult.onFailure { error ->

                fetchListError.postValue(error)
            }
        }
    }

    fun getCharacterDetail(characterId: String) {
        characterDetailUseCase.executeCall(characterId) { result ->

            result.onSuccess {

                characterDetailLiveData.postValue(it)
            }

            result.onFailure { error ->

                fetchDetailError.postValue(error)
            }

        }
    }

    fun getImage(
        imageInfo: Thumbnail,
        origin: AspectRatio.Origin,
        callback: (Result<Bitmap>) -> Unit
    ) {
        imagesUseCase.executeCall(
            Thumbnail(
                imageInfo.path,
                imageInfo.extension
            ), origin
        ) { result ->

            callback(result)
        }
    }


}