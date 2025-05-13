package com.rafaels.data.mapper

import com.rafaels.data.model.ResponseDTO
import com.rafaels.domain.model.MarsRoverModel
import com.rafaels.domain.model.RoverDirectionModel

fun ResponseDTO.toDomainModel(): MarsRoverModel =
    MarsRoverModel(
        topRightCorner = Pair(this.topRightCorner.x + 1, this.topRightCorner.y + 1),
        roverPosition = Pair(this.roverPosition.x, this.roverPosition.y),
        roverInitialDirectionModel = this.roverInitialDirection.toRoverDirectionModel(),
        movements = this.movements,
    )


fun String.toRoverDirectionModel(): RoverDirectionModel {
    return when (this) {
        "N" -> RoverDirectionModel.NORTH
        "S" -> RoverDirectionModel.SOUTH
        "E" -> RoverDirectionModel.EAST
        "W" -> RoverDirectionModel.WEST
        else -> RoverDirectionModel.NORTH //  throw IllegalArgumentException("Unknown dorection character: $this")
    }
}





