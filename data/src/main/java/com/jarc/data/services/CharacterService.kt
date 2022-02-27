package com.jarc.data.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jarc.core.utils.*
import com.jarc.core.utils.Utils.HASH
import com.jarc.core.utils.Utils.TIMESTAMP
import com.jarc.core.utils.Utils.getOffset
import com.jarc.core.utils.Utils.getTimeStampPlusHash
import com.jarc.data.entities.CharactersRawResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CharacterService {

    private var restEndpoints: CharacterEndpoints

    init {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_CHARACTERS_URL)
            .build()

        restEndpoints = retrofit.create(CharacterEndpoints::class.java)
    }


    suspend fun getCharactersList(
        offsetFactor: Int,
        callback: (Result<CharactersRawResponse>) -> Unit
    ) {

        val timeStampPlusHash = getTimeStampPlusHash()

        withContext(Dispatchers.IO) {

            try {
                val result = restEndpoints.getCharactersAsync(
                    offset = offsetFactor.getOffset(),
                    hash = timeStampPlusHash[HASH] ?: "",
                    ts = timeStampPlusHash[TIMESTAMP].toString()
                ).await()

                callback(Result.success(result))

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

    suspend fun getCharacterDetail(
        characterId: String,
        callback: (Result<CharactersRawResponse>) -> Unit
    ) {

        val timeStampPlusHash = getTimeStampPlusHash()

        withContext(Dispatchers.IO) {

            try {
                val result = restEndpoints.getCharacterDetailAsync(
                    hash = timeStampPlusHash[HASH] ?: "",
                    ts = timeStampPlusHash[TIMESTAMP].toString(),
                    character = characterId
                ).await()

                callback(Result.success(result))

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

}

