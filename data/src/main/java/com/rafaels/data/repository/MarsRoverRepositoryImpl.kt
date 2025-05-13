package com.rafaels.data.repository

import com.rafaels.data.remote.MarsRoverApi
import com.rafaels.data.error.ErrorHandler
import com.rafaels.data.mapper.toDomainModel
import com.rafaels.domain.Resource
import com.rafaels.domain.repository.MarsRoverRepository
import com.rafaels.domain.model.MarsRoverModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarsRoverRepositoryImpl(
    private val api: MarsRoverApi,
    private val errorHandler: ErrorHandler,
): MarsRoverRepository {

    override suspend fun getMarsRoverData(): Resource<MarsRoverModel> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getMarsRoverData()

                if (response.isSuccessful) {
                    return@withContext Resource.Success(response.body()!!.toDomainModel())
                } else {
                    return@withContext Resource.Error(
                        errorHandler(
                            response.code(),
                            response.errorBody()
                        )
                    )
                }
            } catch (e: Exception) {
                return@withContext Resource.Error(errorHandler(e))
            }
        }
}