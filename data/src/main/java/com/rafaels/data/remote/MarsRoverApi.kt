package com.rafaels.data.remote

import com.rafaels.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface MarsRoverApi {

    @GET("marsRoverInit.json")
    suspend fun getMarsRoverData(): Response<ResponseDTO>

}