package com.rafaels.domain.model

data class MarsRoverModel(
    val topRightCorner: Pair<Int, Int>,
    val roverPosition: Pair<Int, Int>,
    val roverInitialDirectionModel: RoverDirectionModel,
    val movements: String,
)
