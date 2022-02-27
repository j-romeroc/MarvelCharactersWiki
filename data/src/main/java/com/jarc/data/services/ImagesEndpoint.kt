package com.jarc.data.services

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ImagesEndpoint {

    @GET
    fun getImageAsync(@Url url: String): Deferred<ResponseBody>
}