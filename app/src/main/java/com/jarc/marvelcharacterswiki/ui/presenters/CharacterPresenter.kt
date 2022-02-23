package com.jarc.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import com.jarc.core.utils.AspectRatio
import com.jarc.core.utils.LayerResult
import com.jarc.marvelcharacterswiki.models.CharacterDetailModel
import com.jarc.marvelcharacterswiki.models.CharacterModel
import com.jarc.marvelcharacterswiki.models.Thumbnail

interface CharacterPresenter {

    fun fetchCharacterList(callback: (LayerResult<List<CharacterModel>>) -> Unit)
    fun fetchCharacterDetail(characterId: String, callback:(LayerResult<CharacterDetailModel>) -> Unit)
    fun fetchImage(imageInfo: Thumbnail, origin: AspectRatio.Origin,callback: (LayerResult<Bitmap>) -> Unit)

}