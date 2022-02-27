package com.jarc.domain.usecases

import android.graphics.Bitmap
import com.jarc.core.utils.AspectRatio
import com.jarc.core.utils.Utils.getImageUrl
import com.jarc.domain.models.Thumbnail
import com.jarc.domain.repositories.ImageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImagesUseCase(private val imageRepo: ImageRepo) {

    fun executeCall(
        imageInfo: Thumbnail,
        origin: AspectRatio.Origin,
        callback: (Result<Bitmap>) -> Unit
    ) {

        val url = getImageUrl(
            path = imageInfo.path,
            extension = imageInfo.extension,
            size = AspectRatio.ImageSize.MEDIUM,
            origin = origin
        )

        GlobalScope.launch(Dispatchers.Main) {

            imageRepo.getImage(
                url = url
            ) { result ->

                callback(result)
            }
        }
    }
}