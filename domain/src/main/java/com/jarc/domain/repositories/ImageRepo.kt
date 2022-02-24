package com.jarc.domain.repositories

import android.graphics.Bitmap
import com.jarc.core.utils.LayerResult


interface ImageRepo {

    suspend fun fetchImage(
        url: String,
        callback: (LayerResult<Bitmap>?) -> Unit
    )

    suspend fun getImage(
        url: String,
        callback: (Result<Bitmap>) -> Unit
    )
}