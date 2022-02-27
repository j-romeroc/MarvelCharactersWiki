package com.jarc.domain.repositories

import android.graphics.Bitmap


interface ImageRepo {

    suspend fun getImage(
        url: String,
        callback: (Result<Bitmap>) -> Unit
    )
}