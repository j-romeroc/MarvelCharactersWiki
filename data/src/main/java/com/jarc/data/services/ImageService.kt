package com.jarc.data.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jarc.core.utils.BASE_IMAGE_URL
import com.jarc.core.utils.CustomError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ImageService {

    private var restEndpoints: ImagesEndpoint

    init {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_IMAGE_URL)
            .build()

        restEndpoints = retrofit.create(ImagesEndpoint::class.java)
    }

    suspend fun getImage(url: String, callback: (Result<Bitmap>) -> Unit) {
        withContext(Dispatchers.IO) {

            try {

                val response = restEndpoints.getImageAsync(url).await()

                callback(Result.success(mapToBmp(response.bytes())))

            } catch (e: Throwable) {
                callback(
                    Result.failure(
                        CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = e
                        )
                    )
                )
            }
        }
    }

    private fun mapToBmp(value: ByteArray) = BitmapFactory.decodeByteArray(value, 0, value.size)
}