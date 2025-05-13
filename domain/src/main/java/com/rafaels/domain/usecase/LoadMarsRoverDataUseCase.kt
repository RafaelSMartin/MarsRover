package com.rafaels.domain.usecase

import com.rafaels.domain.Resource
import com.rafaels.domain.repository.MarsRoverRepository
import com.rafaels.domain.model.MarsRoverModel

class LoadMarsRoverDataUseCase(
    private val marsRoverRepository: MarsRoverRepository
) {
    suspend fun getMarsRoverData():Resource<MarsRoverModel> = marsRoverRepository.getMarsRoverData()
}