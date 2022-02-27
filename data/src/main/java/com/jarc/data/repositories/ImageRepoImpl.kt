package com.jarc.data.repositories

import android.graphics.Bitmap
import com.jarc.data.services.ImageService
import com.jarc.domain.repositories.ImageRepo

class ImageRepoImpl(private val service: ImageService) : ImageRepo {

    override suspend fun getImage(
        url: String,
        callback: (Result<Bitmap>) -> Unit
    ) {

        service.getImage(url) { result ->

            callback(result)
        }
    }
}