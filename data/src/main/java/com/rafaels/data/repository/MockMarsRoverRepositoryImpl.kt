package com.rafaels.data.repository

import android.content.Context
import com.google.gson.Gson
import com.rafaels.data.mapper.toDomainModel
import com.rafaels.data.model.ResponseDTO
import com.rafaels.domain.Resource
import com.rafaels.domain.repository.MarsRoverRepository
import com.rafaels.domain.model.MarsRoverModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MockMarsRoverRepositoryImpl(
    private val context: Context,
) : MarsRoverRepository {
    override suspend fun getMarsRoverData(): Resource<MarsRoverModel> =
        withContext(Dispatchers.IO) {
            val jsonFileString = context.getJsonDataFromAsset("mock.json")
            val gson = Gson()
            val result = gson.fromJson(jsonFileString, ResponseDTO::class.java)

            val resultMock = result.toDomainModel()

            return@withContext Resource.Success(resultMock)
        }
}

fun Context.getJsonDataFromAsset(fileName: String): String? = try {
    assets.open(fileName).bufferedReader().use { it.readText() }
} catch (e: IOException) {
    e.printStackTrace()
    null
}


