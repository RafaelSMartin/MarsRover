package com.rafaels.domain.repository

import com.rafaels.domain.Resource
import com.rafaels.domain.model.MarsRoverModel

interface MarsRoverRepository {

    suspend fun getMarsRoverData(): Resource<MarsRoverModel>
}