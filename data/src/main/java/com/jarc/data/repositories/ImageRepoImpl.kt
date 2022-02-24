package com.jarc.data.repositories

import android.graphics.Bitmap
import com.jarc.core.utils.*
import com.jarc.data.services.ImageService
import com.jarc.domain.repositories.ImageRepo

class ImageRepoImpl(private val service: ImageService) : ImageRepo {

    override suspend fun fetchImage(
        url: String,
        callback: (LayerResult<Bitmap>?) -> Unit
    ) {

        service.fetchImage(url) { result ->

            try {
                when (result) {
                    is LayerResult.Success -> {
                        callback(LayerResult.Success(result.value))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError()
                        )
                    }
                }
            } catch (e: Throwable) {

                callback(LayerResult.Error(e))
            }
        }

    }


    override suspend fun getImage(
        url: String,
        callback: (Result<Bitmap>) -> Unit
    ) {

        service.getImage(url) { result ->

            callback(result)
        }

    }
}